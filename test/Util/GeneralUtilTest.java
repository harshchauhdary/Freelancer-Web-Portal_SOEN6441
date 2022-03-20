package Util;

import model.Job;
import model.Project;
import model.User;
import org.json.simple.parser.ParseException;
import org.junit.Before;
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
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class GeneralUtilTest extends WithApplication {

    /**
     * Tests that the custom Executor created in a static block is not null, using assertNotNull.
     */
    @Test
    public void testGetExecutor() {
       assertNotNull(GeneralUtil.getExecutor());
    }

    private WSClient ws;
    private SyncCacheApi cache;

    /**
     * Injection method to inject WsCache
     */
    @Before
    public void injectWsCache() {
        ws = app.injector().instanceOf(WSClient.class);
        cache = app.injector().instanceOf(SyncCacheApi.class);
    }

    /**
     *
     * @throws Exception
     */
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
    public void testGetProjectsFromJson() throws Exception {
        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects2.json");
        List<Project> projects = new ArrayList<Project>();
        Project p = new Project();
        p.setId(33256190);
        p.setOwnerID(33256190);
        p.setTitle("Build me a website");
        p.setDesc("Website");
        p.setTimeSubmitted(new Date((1647794291)));
        p.setType("fixed");
        List<Job> jobs = new ArrayList<Job>();
        Job j = new Job(3,"PHP");
        jobs.add(j);
        p.setSkills(jobs);
        projects.add(p);
        Project p1 = GeneralUtil.getProjectsFromJson(json).get(0);
        assertEquals(p.getId(),p1.getId());
        assertEquals(p.getTitle(),p1.getTitle());
        assertEquals(p.getOwnerID(),p1.getOwnerID());
        assertEquals(p.getType(),p1.getType());
        assertEquals(p.getSkills().get(0).getJob_id(),p1.getSkills().get(0).getJob_id());
        assertEquals(p.getSkills().get(0).getJob_name(),p1.getSkills().get(0).getJob_name());

    }


    @Test
    public void testGetDescriptionFromJson() throws Exception{
        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects.json");
        List<String> l = new ArrayList<>();
        l.add("description one");
        l.add("description two");
        assertEquals(GeneralUtil.getDescriptionFromJson(json), l);
    }

    @Test
    public void testGetUserFromJson() throws IOException, ParseException, ExecutionException, InterruptedException {
        String url="https://www.freelancer.com/api/projects/0.1/projects";
        HashMap<String,String> params = new HashMap<>();
        params.put("owners[]",Long.toString(32136579));
        params.put("full_description","true");
        params.put("job_details","true");
        params.put("limit","10");
        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "User.json");
        User ex_user = new User(32136579,"san6123","san6123","freelancer",1542651206,false,"freelancer","India",false,"Indian Rupee");
        List<Project> projects = new ArrayList<>();
        List<Job> jobs=new ArrayList<>();
        Job job=new Job(1234,"abcd");
        Project p = new Project(32136579,"Hi Igor K., I noticed your profile and would like to offer you my project. We can discuss any details over chat.","Project for Igor K.",1637238113,new ArrayList<>(),"hourly");
        projects.add(p);
        ex_user.setProjects(projects);
        try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
            try (MockedStatic<DescriptionUtil> utilities2 = Mockito.mockStatic(DescriptionUtil.class)) {
                utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache))
                        .thenReturn("s");
                utilities.when(() -> GeneralUtil.getProjectsFromJson("s"))
                        .thenReturn(projects);
                utilities2.when(() -> DescriptionUtil.getReadabilityIndex(projects))
                        .thenReturn(projects);

                User user = UserUtil.getUserFromJson(json, ws, cache);
                assertEquals(ex_user.getId(), user.getId());
                assertEquals(ex_user.getUsername(), user.getUsername());
                assertEquals(ex_user.getDisplay_name(), user.getDisplay_name());
                assertEquals(ex_user.getCountry(), user.getCountry());
                assertEquals(ex_user.getEmail_verified(), user.getEmail_verified());
                assertEquals(ex_user.getLimited_account(), user.getLimited_account());
                assertEquals(ex_user.getProjects().size(), user.getProjects().size());
                assertEquals(ex_user.getReg_date(), user.getReg_date());
                assertEquals(ex_user.getChosen_role(), user.getChosen_role());
                assertEquals(ex_user.getRole(), user.getRole());
            }
        }

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