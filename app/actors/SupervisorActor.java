package actors;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.cache.*;
import play.libs.ws.WSClient;
public class SupervisorActor extends AbstractActor {

    private final ActorRef wsOut;
    private final SyncCacheApi cacheApi;
    private final WSClient ws;
    private ActorRef repositoryDetailsActor = null;
    private ActorRef searchActor = null;
    private ActorRef skillsActor = null;
    private ActorRef statsActor = null;
    private ActorRef userActor = null;
    private String uId ;

    public SupervisorActor(final ActorRef wsOut,WSClient ws, SyncCacheApi syncCacheApi,String uId) {
        this.wsOut =  wsOut;
        this.cacheApi = syncCacheApi;
        this.ws = ws;
        this.uId = uId;
    }

    public static Props props(final ActorRef wsout, WSClient ws ,SyncCacheApi cacheApi,String uId) {
        return Props.create(SupervisorActor.class, wsout,ws, cacheApi,uId);
    }



    @Override
    public void preStart() {
        System.out.println("Supervisor actor created.");
    }



    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(JsonNode.class, this::processRequest)
                .match(Messages.RespondStats.class, r -> wsOut.tell(r.stats, self()))
                .match(Messages.RespondSearch.class, r -> wsOut.tell(r.searchResult, self()))
                .match(Messages.SkillsDetails.class, r -> wsOut.tell(r.skillsDetails, self()))
                .match(Messages.UserDetails.class, r -> wsOut.tell(r.user, self()))
                .build();
    }

    private  void processRequest(JsonNode receivedJson) {
        ObjectMapper mapper = new ObjectMapper();
        if(receivedJson.has("searchPage")) {

            if(searchActor==null)
            {
               // log.info("Creating a search page actor.");
              searchActor = getContext().actorOf(SearchActor.props(self(), ws, cacheApi,uId));
            }
            String query = receivedJson.get("searchPage").asText();
           searchActor.tell(new Messages.GetSearchResult(query), getSelf());

        }else if(receivedJson.has("statsQuery")){

            if(statsActor == null){
                statsActor = getContext().actorOf(StatsActor.props(self(),ws,cacheApi));
            }
            String query = receivedJson.get("statsQuery").asText();
            statsActor.tell(new Messages.GetStats(query), getSelf());
        }
        else if(receivedJson.has("jobId")){

            if(skillsActor == null){
                skillsActor = getContext().actorOf(SkillsActor.props(self(), cacheApi, ws));
            }
            String query = receivedJson.get("jobId").asText();
             skillsActor.tell(new Messages.GetSkills(query), getSelf());
        }
        else if(receivedJson.has("userId")){

            if(userActor == null){
                userActor = getContext().actorOf(UserActor.props(self(),ws,cacheApi));
            }
            String query = receivedJson.get("userId").asText();
             userActor.tell(new Messages.GetUser(query), getSelf());
        }
        else if(receivedJson.has("description")){

            if(statsActor == null){
                statsActor = getContext().actorOf(StatsActor.props(self(),ws,cacheApi));
            }
            String query = receivedJson.get("description").asText();
            statsActor.tell(new Messages.GetIndiStats(query), getSelf());
        }
    }
}
