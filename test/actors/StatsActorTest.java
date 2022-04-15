package actors;

import Util.Command;
import Util.GeneralUtil;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.*;
import akka.actor.ActorRef;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import play.test.WithApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatsActorTest extends WithApplication {
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
    public void testGetStats() throws IOException {

        new TestKit(actorSystem) {
            {
                String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
                HashMap<String, String> params = new HashMap<>();
                params.put("query", "react-native");
                params.put("sort_field", "time_updated");
                params.put("compact", "false");
                params.put("offset", "0");

                HashMap<String, String> params2 = new HashMap<>();
                params2.put("query", "react-native");
                params2.put("sort_field", "time_updated");
                params2.put("compact", "false");
                params2.put("offset", "100");

                HashMap<String, String> params3 = new HashMap<>();
                params3.put("query", "react-native");
                params3.put("sort_field", "time_updated");
                params3.put("compact", "false");
                params3.put("offset", "200");
                params3.put("limit", "50");

                List<String> descriptions = Arrays.asList("Hello sdas sd ", "World!", "How sda s");
                String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects.json");

                try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
                    utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache))
                            .thenReturn(json);
                    utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params2, ws, cache))
                            .thenReturn(json);
                    utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params3, ws, cache))
                            .thenReturn(json);
                    utilities.when(() -> GeneralUtil.getDescriptionFromJson(json))
                            .thenReturn(descriptions);

                    final ActorRef statsActor = actorSystem.actorOf(
                            StatsActor.props(testProbe.getRef(), ws, cache));
                    statsActor.tell(new Messages.GetStats("JAVA"), testProbe.getRef());
                    Messages.RespondStats response = testProbe.expectMsgClass(Messages.RespondStats.class);
                    assertTrue(response.toString().contains("statsResults"));
//            List<String> repoNames = new ArrayList<>();
//            for(JsonNode repository: topicResponse.topicDetails.get("searchProfile").get("repos")){
//                repoNames.add(repository.get("name").asText());
//                //System.out.println(repoNames);
//            }
//            assertEquals(Arrays.asList("name1","name2"),repoNames);

                }
            }
        };
   }

    @Test
    public void testIndiStats() {
        new TestKit(actorSystem) {
            {
                final ActorRef statsActor = actorSystem.actorOf(
                        StatsActor.props(testProbe.getRef(), ws, cache));
                statsActor.tell(new Messages.GetIndiStats("JAVA is-a.language is"), testProbe.getRef());
                Messages.RespondStats response = testProbe.expectMsgClass(Messages.RespondStats.class);
                assertTrue(response.toString().contains("statsResults"));

            }
        };
    }

    /**
     * Reads a JSON file from path and returns content as a String
     * @param path location of file that contains the JSON file
     * @return returns the content as a String
     * @throws IOException
     */

    public static String getJsonFileAsString(String path) throws IOException {
        String filePath = new File("").getAbsolutePath();
        byte[] encoded = Files.readAllBytes(Paths.get(filePath.concat(path)));
        return new String(encoded, "UTF-8");
    }

}