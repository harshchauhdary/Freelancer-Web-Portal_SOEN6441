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

import static actors.StatsActorTest.getJsonFileAsString;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchActorTest extends WithApplication {
    private WSClient ws;
    private SyncCacheApi cache;
    static ActorSystem actorSystem;
    private static TestKit testProbe;
    private String uID;

    @Before
    public void injectWsCache() {

        ws = app.injector().instanceOf(WSClient.class);
        cache = app.injector().instanceOf(SyncCacheApi.class);
        actorSystem = ActorSystem.create();
        testProbe = new TestKit(actorSystem);
    }
    @Test
    public void onGetSearchTest() throws IOException {
        new TestKit(actorSystem) {
            {
                String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
                HashMap<String, String> params = new HashMap<>();
                params.put("query", "react-native");
                params.put("sort_field", "time_updated");
                params.put("compact", "false");
                params.put("offset", "0");

                List<String> descriptions = Arrays.asList("Hello sdas sd ", "World!", "How sda s");
                String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects.json");

                try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
                    utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache))
                            .thenReturn(json);
                    final ActorRef searchActor = actorSystem.actorOf(
                            SearchActor.props(testProbe.getRef(), ws, cache, uID));
                    searchActor.tell(new Messages.GetSearchResult("JAVA"), testProbe.getRef());
                    Messages.RespondSearch response = testProbe.expectMsgClass(Messages.RespondSearch.class);

                    assertTrue(response.toString().contains("projects"));
                }
            }
        };

    }
    @Test
    public void onTick(){
        new TestKit(actorSystem) {
            {

                final ActorRef searchActor = actorSystem.actorOf(
                        SearchActor.props(testProbe.getRef(), ws, cache, uID));
                searchActor.tell(new SearchActor.Tick(), testProbe.getRef());

            }
        };
    }
}
