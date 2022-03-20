package Util;

import junit.framework.TestCase;
import model.Project;
import model.User;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import play.test.Helpers;
import play.test.WithApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;

public class GeneralUtilTest extends WithApplication {

    @Test
    public void testGetExecutor() {
       assertNotNull(GeneralUtil.getExecutor());
    }

    private WSClient ws;
    private SyncCacheApi cache;

    @Before
    public void injectWsCache() {
        ws = app.injector().instanceOf(WSClient.class);
        cache = app.injector().instanceOf(SyncCacheApi.class);
    }

    @Test
    public void testGetJsonResponseFromUrl() throws Exception {
        String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
        HashMap<String, String> params = new HashMap<>();
        params.put("query", "react-native");
        params.put("compact", "false");
        params.put("limit", "1");

        String response = GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache);
        assertTrue(response.contains("status\":\"success"));

        String response3 = GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache);
        assertTrue(response3.contains("status\":\"success"));

        String response2 = GeneralUtil.getJsonResponseFromUrl(url, null, ws, cache);
        assertTrue(response2.contains("status\":\"success"));


    }

    @Test
    public void testGetProjectsFromJson() throws IOException {

    }

    @Test
    public void testGetDescriptionFromJson() throws Exception{
        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects.json");
        List<String> l = new ArrayList<>();
        l.add("description one");
        l.add("description two");
        assertEquals(GeneralUtil.getDescriptionFromJson(json), l);

        String empty_json = "";
        List<String> empty_l = new ArrayList<>();
        assertEquals(GeneralUtil.getDescriptionFromJson(empty_json), empty_l);
    }

    @Test
    public void testGetUserFromJson() throws IOException, ParseException, ExecutionException, InterruptedException {
//        String url="https://www.freelancer.com/api/projects/0.1/projects";
//        HashMap<String,String> params = new HashMap<>();
//        params.put("owners[]",Long.toString(36471202));
//        params.put("full_description","true");
//        params.put("job_details","true");
//        params.put("limit","10");
//        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "User.json");
//      //  User ex_user = new User();
//        List<Project> projects = new ArrayList<>();
//      //  Project p = new Project();
//        projects.add(p);
//        ex_user.setProjects(projects);
//        try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
//            try (MockedStatic<DescriptionUtil> utilities2 = Mockito.mockStatic(DescriptionUtil.class)) {
//                utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache))
//                        .thenReturn("s");
//                utilities.when(() -> GeneralUtil.getProjectsFromJson("s"))
//                        .thenReturn(projects);
//                utilities2.when(() -> DescriptionUtil.getReadabilityIndex(projects))
//                        .thenReturn(projects);
//
//                User user = UserUtil.getUserFromJson(json,ws,cache);
//                assertEquals(ex_user.getId(),user.getId());
//            }
//        }

    }

    @Test
    public void testGenerateId() {
        Map<String, String> h = new HashMap<String, String>();
        h.put("sdhjak43hj","hgtrhy");
        h.put("324cv3sdcv","2345d");
        String id = GeneralUtil.generateId(h.keySet());
        assertFalse(h.containsKey(id));
    }

    public static String getJsonFileAsString(String path) throws IOException {
        String filePath = new File("").getAbsolutePath();
        byte[] encoded = Files.readAllBytes(Paths.get(filePath.concat(path)));

        return new String(encoded, "UTF-8");
    }
}