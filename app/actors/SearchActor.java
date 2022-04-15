package actors;

//import Helper.SessionHelper;

import Util.GeneralUtil;
import Util.Session;
import Util.*;
import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import model.Canva;
import model.Project;
import org.json.simple.parser.ParseException;
import play.cache.*;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import models.SearchResults;
import play.libs.ws.WSClient;
//import services.GithubService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static akka.pattern.Patterns.ask;

public class SearchActor extends AbstractActorWithTimers {

    private String uId;
    private ActorRef supervisorActor;
    private SyncCacheApi cacheApi;
    private WSClient ws;

    public SearchActor(ActorRef supervisorActor, WSClient ws, SyncCacheApi cacheApi, String uId) {
        this.supervisorActor = supervisorActor;
        this.cacheApi = cacheApi;
        this.uId = uId;
        this.ws = ws;
    }

    public static Props props(final ActorRef wsout, WSClient ws, SyncCacheApi cacheApi, String uId) {
        return Props.create(SearchActor.class, wsout, ws, cacheApi, uId);
    }

    @Override
    public void preStart() {
        System.out.println("SearchActor actor created.");
        getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(10, TimeUnit.SECONDS));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.GetSearchResult.class, Query -> {
                    onGetSearch(Query).thenAcceptAsync(this::processSearchResult);
                })
                .match(SearchActor.Tick.class, q -> {
                    onTimer();
                })
                .build();
    }
    public static final class Tick {
    }
    private void onTimer() {
        List<Canva> canvas = Session.getDataFromSession(uId);
        canvas.forEach(c ->{
            Canva c1 = getCanva(c.getTitle());
            c.getProjects().forEach(p -> {
                if(c1.getProjects().contains(p)){
                    c1.getProjects().remove(p);
                }
            });
            c.getProjects().addAll(c1.getProjects());

        });
        Session.setDataForSession(uId,canvas);
    }


    private CompletionStage<JsonNode> onGetSearch(Messages.GetSearchResult query) throws IOException, ExecutionException, InterruptedException {

        return CompletableFuture.supplyAsync(() -> {
            List<Canva> clist = null;
            try {


                String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
                HashMap<String, String> params = new HashMap<>();
                params.put("query", query.query);
                params.put("job_details", "true");
                params.put("compact", "false");
                params.put("limit", "10");
                String jsonResponse = null;
                List<Project> projects = null;
                jsonResponse = GeneralUtil.getJsonResponseFromUrl(url, params, ws, cacheApi);
                projects = GeneralUtil.getProjectsFromJson(jsonResponse);
                System.out.println("addw");
                projects = DescriptionUtil.getReadabilityIndex(projects);

                System.out.println("adwdaadawdawda");
                if (projects.size() == 0) {
                    return new ArrayList<>();
                }
                double averageIndex = 0;


                averageIndex = DescriptionUtil.getAverageReadabilityIndex(projects);


                Canva c = new Canva(query.query, averageIndex, projects);
                clist = Session.getDataFromSession(uId);
                boolean checkIfPresent = clist.stream().map(Canva::getTitle).anyMatch(r -> r.equals(c.getTitle()));

                if (checkIfPresent) {
                    return clist;
                }
                clist.add(c);
                Session.setDataForSession(uId, clist);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return clist;
        }).thenApplyAsync(searchResults -> {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode searchData = mapper.createObjectNode();
            searchData.put("responseType", "searchResults");
            JsonNode searchMapJsonNode = mapper.convertValue(searchResults, JsonNode.class);
            System.out.println(searchMapJsonNode);
            searchData.set("searchMap", searchMapJsonNode);
            System.out.println(searchData);
            return searchData;
        });
    }

    private Canva getCanva(String Query) {
        Canva c1 = null;
        try {
            String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
            HashMap<String, String> params = new HashMap<>();
            params.put("query", Query);
            params.put("job_details", "true");
            params.put("compact", "false");
            params.put("limit", "10");
            String jsonResponse = null;
            List<Project> projects = null;
            ActorRef descActor = getContext().actorOf(DescriptionUtilActor.props(self()), "desc");


            jsonResponse = GeneralUtil.getJsonResponseFromUrl(url, params, ws, cacheApi);
            projects = GeneralUtil.getProjectsFromJson(jsonResponse);

            projects = GeneralUtil.getProjectsFromJson(jsonResponse);

            projects = DescriptionUtil.getReadabilityIndex(projects);
            double averageIndex = 0;


            averageIndex = DescriptionUtil.getAverageReadabilityIndex(projects);


            c1 = new Canva(Query, averageIndex, projects);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return c1;
    }

    private void processSearchResult(JsonNode searchResult) {
        supervisorActor.tell(new Messages.RespondSearch(searchResult), getSelf());
    }
}
