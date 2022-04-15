package actors;

import Util.GeneralUtil;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Project;
import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static Util.GeneralUtil.getJsonResponseFromUrl;

public class UserActor extends AbstractActor {

    private ActorRef supervisorActor;
    WSClient ws;
    SyncCacheApi cacheApi;
    private List<Project> projects;
    User user;


    public UserActor(ActorRef supervisorActor,WSClient ws,SyncCacheApi cacheApi){
        this.supervisorActor=supervisorActor;
        this.ws=ws;
        this.cacheApi=cacheApi;
    }

    public static Props props(ActorRef supervisorActor,WSClient ws,SyncCacheApi cacheApi){
        return Props.create(UserActor.class,supervisorActor,ws,cacheApi);
    }

    @Override
    public void preStart() {
        System.out.println("User Actor Started");
    }

   // private CompletionStage<JsonNode>

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Messages.GetUser.class,userDetailsRequest -> {
            getUserDetails(userDetailsRequest);
        }).match(Messages.RespondGetReadabilityIndex.class, rindex -> {
            onRespondGetReadabilityIndex(rindex).thenAcceptAsync(this::processUser);;
        }).build();
    }

    private void processUser(JsonNode jsonNode) {
        supervisorActor.tell(new Messages.UserDetails(jsonNode), getSelf());
    }

    private CompletionStage<JsonNode> onRespondGetReadabilityIndex(Messages.RespondGetReadabilityIndex r) {

        return CompletableFuture.supplyAsync(()->{
            user.setProjects(r.projects);
            System.out.println(user.getId());
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode userData = mapper.createObjectNode();
            userData.put("responseType", "userDetails");
            JsonNode userMapJsonNode = mapper.convertValue(user, JsonNode.class);
            userData.set("user", userMapJsonNode);
            System.out.println(userData);
            return userData;
        });
    }

//    private Behavior<Command> onAskUser(JsonNode userData) {
////        a.replyTo.tell(new UserUtilActor.RespondGetUser(user));
////        return this;
//        //supervisorActor.tell();
//    }

    private void getUserDetails(Messages.GetUser gu) throws Exception{

        String url = "https://www.freelancer.com/api/users/0.1/users/" + gu.id;
        String jsonRespone = GeneralUtil.getJsonResponseFromUrl(url, null, ws, cacheApi);

        JSONParser parser=new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(jsonRespone);
        JSONObject jsonObject=(JSONObject) jObj.get("result");
        JSONObject jsonObject1=(JSONObject) jsonObject.get("location");
        JSONObject jsonObject2=(JSONObject)jsonObject1.get("country");
        JSONObject jsonObject3=(JSONObject)jsonObject.get("status");
        JSONObject jsonObject4=(JSONObject)jsonObject.get("primary_currency");


        String username= (String) jsonObject.get("username");
        long id = (long) jsonObject.get("id");
        long reg_date=(long)jsonObject.get("registration_date");
        boolean limited_account=(boolean) jsonObject.get("limited_account");
        String display_name = (String) jsonObject.get("display_name");
        String role = (String) jsonObject.get("role");
        String chosen_role=(String)jsonObject.get("chosen_role");
        String country=(String)jsonObject2.get("name");
        boolean email_verified=(boolean)jsonObject3.get("email_verified");
        String primary_currency=(String)jsonObject4.get("name");

        user=new User(id,username,display_name,role,reg_date,limited_account,chosen_role,country,email_verified,primary_currency);

        String url2="https://www.freelancer.com/api/projects/0.1/projects";
        HashMap<String,String> params = new HashMap<>();
        params.put("owners[]",Long.toString(id));
        params.put("full_description","true");
        params.put("job_details","true");
        params.put("limit","10");

        String data=getJsonResponseFromUrl(url2,params,ws,cacheApi);

        List<Project> projects_json = GeneralUtil.getProjectsFromJson(data);

        ActorRef descActor = getContext().actorOf(DescriptionUtilActor.props(self()), "desc2");

        descActor.tell(new Messages.GetReadabilityIndex(projects_json), getContext().getSelf());
        // ActorRef<Command> s= getContext().spawn(skillsActor.create(),"hello");
        // s.tell(new skillsActor.GetJSONResponse(url,params, gu.ws, gu.cacheApi, getContext().getSelf()));
        // ActorRef<Command> s2=getContext().spawn(DescriptionUtilActor.create(),"hello");
//
        //ActorRef desActor=getContext().actorOf(DescriptionUtilActor.props(),"name");

    }
}
