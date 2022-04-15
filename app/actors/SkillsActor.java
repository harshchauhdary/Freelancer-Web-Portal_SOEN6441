package actors;

import actors.DescriptionUtilActor;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.typed.Behavior;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Project;
import org.json.simple.parser.ParseException;
import play.cache.AsyncCacheApi;
import Util.GeneralUtil;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class SkillsActor extends AbstractActor {
    private double average;
    private List<Project> projects;

    private ActorRef supervisorActor;
    private SyncCacheApi syncCacheApi;
    WSClient ws;


    /**
     * @param supervisorActor Actor reference for the supervisor actor
     * @param syncCacheApi to utilize the asynchronous chache
     */


    public SkillsActor(ActorRef supervisorActor, SyncCacheApi syncCacheApi,WSClient ws) {
        this.supervisorActor = supervisorActor;
        this.syncCacheApi = syncCacheApi;
        this.ws = ws;
    }

    public static Props props(ActorRef supervisorActor, SyncCacheApi syncCacheApi, WSClient ws) {
        return Props.create(SkillsActor.class, supervisorActor, syncCacheApi, ws);
    }

    @Override
    public void preStart() {
        System.out.println("Skills actor created.");
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.GetSkills.class, skillsInfo -> {
                    onGetSkills(skillsInfo);
                })
                .match(Messages.RespondGetReadabilityIndex.class, rindex -> {
                    onRespondGetReadabilityIndex(rindex);
                })
                .match(Messages.RespondGetAverage.class, raverage -> {
                    onRespondGetAverage(raverage).thenAcceptAsync(this::processGetAverage);
                })
                .build();


    }

    private void processGetAverage(JsonNode skillsInfo) {
        //tell supervisor
        System.out.println(skillsInfo);
        supervisorActor.tell(new Messages.SkillsDetails(skillsInfo), getSelf());
    }

    private CompletionStage<JsonNode> onRespondGetAverage(Messages.RespondGetAverage raverage) {
        return CompletableFuture.supplyAsync(()->{
            average = raverage.average;
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode skillsData = mapper.createObjectNode();
            skillsData.put("responseType", "skillsDetails");
            JsonNode skillsMapJsonNode = mapper.convertValue(projects, JsonNode.class);
            JsonNode averageMapJsonNode = mapper.convertValue(average, JsonNode.class);
            skillsData.set("projects", skillsMapJsonNode);
            skillsData.set("average", averageMapJsonNode);
            return skillsData;
        });
    }

    private void onRespondGetReadabilityIndex(Messages.RespondGetReadabilityIndex r) {
        projects = r.projects;
        ActorRef descActor = getContext().actorOf(DescriptionUtilActor.props(self()), "desc1");
        descActor.tell(new Messages.GetAverage(projects), getContext().getSelf());
    }


    private void onGetSkills(Messages.GetSkills g) throws ExecutionException, InterruptedException, IOException, ParseException {
        HashMap<String, String> params = new HashMap<>();
        String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
        params.put("job_details", "true");
        params.put("compact", "false");
        params.put("jobs[]", g.jobId);
        params.put("limit", "10");

        String jsonResponse = GeneralUtil.getJsonResponseFromUrl(url, params, ws,syncCacheApi);
        List<Project> projects_json = GeneralUtil.getProjectsFromJson(jsonResponse);

        ActorRef descActor = getContext().actorOf(DescriptionUtilActor.props(self()), "desc");

        descActor.tell(new Messages.GetReadabilityIndex(projects_json), getContext().getSelf());
        // adding user reference to private variable.

    }
}