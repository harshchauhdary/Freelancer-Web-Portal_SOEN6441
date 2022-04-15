package actors;

import Util.Command;
import Util.GeneralUtil;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Project;
import org.json.simple.parser.ParseException;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Akka Actor to get the word statistics for a given query.
 */
public class StatsActor extends AbstractActor {

    private akka.actor.ActorRef supervisorActor;
    private SyncCacheApi cacheApi;
    private WSClient ws;

    public StatsActor(akka.actor.ActorRef supervisorActor, WSClient ws, SyncCacheApi cacheApi) {
        this.supervisorActor = supervisorActor;
        this.cacheApi = cacheApi;
        this.ws = ws;
    }

    public static Props props(final akka.actor.ActorRef wsout, WSClient ws , SyncCacheApi cacheApi) {
        return Props.create(StatsActor.class, wsout,ws, cacheApi);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.GetStats.class, Query -> {
                    onGetStats(Query).thenAcceptAsync(this::processStats);
                })
                .match(Messages.GetIndiStats.class, Description -> {
                    onGetIndiStats(Description).thenAcceptAsync(this::processStats);
                })
                .build();
    }



    /**
     * Method to be executed when we get message for GetIndiStats protocol, calculates statistics and sends them back as a message.
     * @param g Object for GetIndiStats protocol
     * @return  this
     * @author Harsh
     */
    private CompletionStage<JsonNode> onGetIndiStats(Messages.GetIndiStats g) {
        return CompletableFuture.supplyAsync(()-> {
                    List<String> result = new ArrayList<>();
                    result.add(g.description);
                    LinkedHashMap<String, Long> stats = getStatsFromDescriptions(result);
                    return stats;
                }).thenApplyAsync(stats -> {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode statsData = mapper.createObjectNode();
            statsData.put("responseType", "statsResults");
            JsonNode statsMapJsonNode = mapper.convertValue(stats, JsonNode.class);
            statsData.set("stats", statsMapJsonNode);
            return statsData;
        });
    }

    /**
     * Method to be executed when we get message for GetStats protocol(asking for global stats), calculates statistics
     * and sends them back as a message.
     * @param g bject for GetStats protocol
     * @return this
     * @throws IOException
     * @throws ParseException
     * @throws ExecutionException
     * @throws InterruptedException
     * @author Harsh
     */
    private CompletionStage<JsonNode> onGetStats(Messages.GetStats g) throws IOException, ParseException, ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(()->{
            LinkedHashMap<String, Long> stats = null;
            try {
                List<String> result = new ArrayList<>();
                String encodeQuery = java.net.URLEncoder.encode(g.query, "UTF-8");

                String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
                HashMap<String, String> params = new HashMap<>();
                params.put("query", encodeQuery);
                params.put("sort_field", "time_updated");
                params.put("compact", "false");
                params.put("offset", "0");
                String response1 = GeneralUtil.getJsonResponseFromUrl(url, params, ws, cacheApi);
                params.remove("offset");
                params.put("offset", "100");
                String response2 = GeneralUtil.getJsonResponseFromUrl(url, params, ws, cacheApi);
                params.remove("offset");
                params.put("offset", "200");
                params.put("limit", "50");
                String response3 = GeneralUtil.getJsonResponseFromUrl(url, params, ws, cacheApi);

                result.addAll(GeneralUtil.getDescriptionFromJson(response1));
                result.addAll(GeneralUtil.getDescriptionFromJson(response2));
                result.addAll(GeneralUtil.getDescriptionFromJson(response3));


                stats = getStatsFromDescriptions(result);


            }
            catch(Exception e){
                e.printStackTrace();
            }
            return stats;
        }).thenApplyAsync(stats -> {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode statsData = mapper.createObjectNode();
            statsData.put("responseType", "statsResults");
            JsonNode statsMapJsonNode = mapper.convertValue(stats, JsonNode.class);
            statsData.set("stats", statsMapJsonNode);
            System.out.println(statsData);
            return statsData;
        });


//        g.replyTo.tell(new StatsActor.RespondGetStats(stats));
    }
    private void processStats(JsonNode jsonNode) {
        supervisorActor.tell(new Messages.RespondStats(jsonNode), getSelf());
    }


    /**
     * Calculates word statistics from a list of strings.
     * @author Harsh 40201627
     * @param disc_list List of preview descriptions of the projects.
     * @return Returns a Map&lt;String, Long&gt; containing words as keys and their frequency as values
     * @author Harsh
     * @see Project
     */
    private static LinkedHashMap<String, Long> getStatsFromDescriptions(List<String> disc_list){
        // getting words using stream, regex and matcher
        Stream<String> a = disc_list.stream().flatMap(d -> {
            List<String> w = new ArrayList<>();
            Matcher matcher
                    = Pattern.compile("(\\w+)")
                    .matcher(d);
            while (matcher.find()) {
                // get the group matched using group() method
                w.add(matcher.group());
            }
            return w.stream();
        });

        // calculating frequency of each word and storing in a map
        Map<String, Long> data = a.collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ));

        return sortStats(data);

    }

    /**
     * Sorts map by value in descending order using LinkedHashMap(as it stores in same order as insertion)
     * @author Harsh 40201627
     * @param stats Map&lt;String, Long&gt; containing words as keys and their frequency as values
     * @return Returns a LinkedHashMap&lt;String, Long&gt; containing sorted statistics
     * @author Harsh
     */
    private static LinkedHashMap<String, Long> sortStats(Map<String, Long> stats){
        LinkedHashMap<String, Long> sortedStats = new LinkedHashMap<>();

        //sorting and adding to the LinkedHashMap
        stats.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedStats.put(x.getKey(), x.getValue()));

        return sortedStats;
    }

}
