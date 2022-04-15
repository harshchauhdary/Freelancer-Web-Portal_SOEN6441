package actors;

import Util.GeneralUtil;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.Before;
import org.junit.Test;
import akka.actor.ActorRef;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import play.test.WithApplication;
//import org.scalatestplus.junit.JUnitSuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class skillsActorTest extends WithApplication {
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
    public void testGetSkills() throws IOException {

        new TestKit(actorSystem) {{

            String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
            HashMap<String, String> params = new HashMap<>();
        params.put("query","react-native");
        params.put("sort_field","time_updated");
        params.put("compact","false");
        params.put("offset","0");


            List<String> descriptions = Arrays.asList("Hello sdas sd ", "World!", "How sda s");
            String json = "";
        try(
            MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class))

            {
                utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache))
                        .thenReturn(json);


                final ActorRef skillsActor = actorSystem.actorOf(
                        SkillsActor.props(testProbe.getRef(), cache, ws));
                skillsActor.tell(new Messages.GetSkills("3"), testProbe.getRef());
                Messages.SkillsDetails response = testProbe.expectMsgClass(Messages.SkillsDetails.class);
                assertTrue(response.toString().contains(""));
            }
        }};

    }

    @Test
    public void testRespondGetAverage()

    {
        new TestKit(actorSystem) {
            {
                final ActorRef skillsActor = actorSystem.actorOf(
                        SkillsActor.props(testProbe.getRef(), cache, ws));
                skillsActor.tell(new Messages.GetSkills("3"), testProbe.getRef());
                Messages.SkillsDetails response = testProbe.expectMsgClass(Messages.SkillsDetails.class);

                //void assertTrue(response.toString().contains(""));
            }
            };
    }
//    public static String getJsonFileAsString(String path) throws IOException {
//        new TestKit(actorSystem) {
//            String filePath = new File("").getAbsolutePath();
//            byte[] encoded = Files.readAllBytes(Paths.get(filePath.concat(path)));
//            return new String(encoded, "UTF-8");
//        }
//    }


}






