package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SupervisorActorTest extends WithApplication {
    static ActorSystem actorSystem;
    private static TestKit testProbe;
    private WSClient ws;
    private SyncCacheApi cache;
    @Before
    public void injectWsCache() {
        ws = app.injector().instanceOf(WSClient.class);
        cache = app.injector().instanceOf(SyncCacheApi.class);
        actorSystem = ActorSystem.create();
        testProbe = new TestKit(actorSystem);
    }

    @Test
    public void testRespondStats(){
        new TestKit(actorSystem) {
            {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode statsData = mapper.createObjectNode();
                statsData.put("responseType", "statsResults");

                final ActorRef supervisorActor = actorSystem.actorOf(
                        SupervisorActor.props(testProbe.getRef(), ws, cache, "123"));
                supervisorActor.tell(new Messages.RespondStats(statsData), testProbe.getRef());

                JsonNode response = testProbe.expectMsgClass(JsonNode.class);
                assertTrue(response.toString().contains("statsResults"));
            }
        };
    }

    @Test
    public void testRespondSearch(){
        new TestKit(actorSystem) {
            {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode searchData = mapper.createObjectNode();
                searchData.put("responseType", "searchResults");

                final ActorRef supervisorActor = actorSystem.actorOf(
                        SupervisorActor.props(testProbe.getRef(), ws, cache, "123"));
                supervisorActor.tell(new Messages.RespondSearch(searchData), testProbe.getRef());

                JsonNode response = testProbe.expectMsgClass(JsonNode.class);
                assertTrue(response.toString().contains("searchResults"));
            }
        };
    }
    @Test
    public void testSkillsDetails(){
        new TestKit(actorSystem) {
            {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode skillsData = mapper.createObjectNode();
                skillsData.put("responseType", "skillsDetails");

                final ActorRef supervisorActor = actorSystem.actorOf(
                        SupervisorActor.props(testProbe.getRef(), ws, cache, "123"));
                supervisorActor.tell(new Messages.SkillsDetails(skillsData), testProbe.getRef());

                JsonNode response = testProbe.expectMsgClass(JsonNode.class);
                assertTrue(response.toString().contains("skillsDetails"));
            }
        };
    }
    @Test
    public void testUserDetails(){
        new TestKit(actorSystem) {
            {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode userData = mapper.createObjectNode();
                userData.put("responseType", "userDetails");

                final ActorRef supervisorActor = actorSystem.actorOf(
                        SupervisorActor.props(testProbe.getRef(), ws, cache, "123"));
                supervisorActor.tell(new Messages.UserDetails(userData), testProbe.getRef());

                JsonNode response = testProbe.expectMsgClass(JsonNode.class);
                assertTrue(response.toString().contains("userDetails"));
            }
        };
    }

    @Test
    public void testJsonNode(){
        new TestKit(actorSystem) {
            {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode searchData = mapper.createObjectNode();
                searchData.put("searchPage", "react");

                final ActorRef supervisorActor = actorSystem.actorOf(
                        SupervisorActor.props(testProbe.getRef(), ws, cache, "123"));
                supervisorActor.tell(searchData, testProbe.getRef());

                Messages.GetSearchResult response = testProbe.expectMsgClass(Messages.GetSearchResult.class);
                assertEquals(response.query, "react");


                ObjectNode statsData = mapper.createObjectNode();
                statsData.put("statsQuery", "react");
                supervisorActor.tell(statsData, testProbe.getRef());

                Messages.GetStats response2 = testProbe.expectMsgClass(Messages.GetStats.class);
                assertEquals(response.query, "react");


                ObjectNode skillsData = mapper.createObjectNode();
                skillsData.put("jobId", "3");
                supervisorActor.tell(skillsData, testProbe.getRef());

                Messages.GetStats response3 = testProbe.expectMsgClass(Messages.GetStats.class);
                assertEquals(response.query, "3");


                ObjectNode userData = mapper.createObjectNode();
                userData.put("userId", "123456");
                supervisorActor.tell(userData, testProbe.getRef());

                Messages.GetStats response4 = testProbe.expectMsgClass(Messages.GetStats.class);
                assertEquals(response.query, "123456");


                ObjectNode indiStatsData = mapper.createObjectNode();
                indiStatsData.put("description", "java-is a language");
                supervisorActor.tell(indiStatsData, testProbe.getRef());

                Messages.GetStats response5 = testProbe.expectMsgClass(Messages.GetStats.class);
                assertEquals(response.query, "java-is a language");
            }
        };

    }
}