package actors;

import Util.GeneralUtil;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.testkit.javadsl.TestKit;
import junit.framework.TestCase;
import model.Job;
import model.Project;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import play.test.WithApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserActorTest extends WithApplication {

    private WSClient ws;
    private SyncCacheApi cache;
    static ActorSystem actorSystem;
    private static TestKit testProbe;

    @Before
    public void injectWsCache() {

        ws = app.injector().instanceOf(WSClient.class);
        cache = app.injector().instanceOf(SyncCacheApi.class);
        actorSystem = ActorSystem.create();
        testProbe = new TestKit(actorSystem);
    }

    @Test
    public void testGetUserDetails(){
        new TestKit(actorSystem) {
            {

        //String url = "https://www.freelancer.com/api/users/0.1/users/32136579";

       // String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects2.json");

        /**try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
            utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, null, ws, cache))
                    .thenReturn(json);**/
            final ActorRef userActor = actorSystem.actorOf(UserActor.props(testProbe.getRef(), ws, cache));
            userActor.tell(new Messages.GetUser("32136579"), testProbe.getRef());
            //Messages.UserDetails response = testProbe.expectMsg(Messages.UserDetails.class);
            }
        };

        }

    @Test
    public void testGetReadabilityIndex() {
        new TestKit(actorSystem) {
            {
                List<Project> projects = new ArrayList<>();
                projects.add(new Project(58976525, "Introduction:\\nEmbedded hellet systems UG  is a German Engineering company in Cologne. We are looking for Sen", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed"));
                final ActorRef userActor = actorSystem.actorOf(UserActor.props(testProbe.getRef(), ws, cache));
                // userActor.tell(new Messages.GetUser("32136579"),testProbe.getRef());
                userActor.tell(new Messages.RespondGetReadabilityIndex(projects), testProbe.getRef());
            }
        };
    }

}