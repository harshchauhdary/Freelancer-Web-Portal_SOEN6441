package controllers;

import Util.DescriptionUtil;
import Util.UserUtil;
import controllers.HomeController;
import model.Job;
import model.Project;
import model.User;
import org.junit.Before;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import Util.GeneralUtil;
import Util.StatsUtil;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.test.Helpers;

import play.mvc.Http.RequestBuilder;
import org.mockito.Mockito;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import javax.inject.Inject;

/**
 * Test class for HomeController.
 */
public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    /**
     * Test method for index
     */
    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Http.RequestBuilder request1 = new Http.RequestBuilder()
                .method(GET)
                .uri("/");
        request1.session("user","hdalkhdlkjdla");
        Result result = route(app, request);
        Result result1 = route(app, request1);

        assertEquals(OK, result.status());
        assertEquals(OK, result1.status());

    }
    /**
     * Test method for home method of HomeController class
     * Compares the attributes obtained from mock with the one passed static
     * Mocks getJsonResponseFromUrl and getReadabiltityIndex of GeneralUtil class and makes the get request using requestbuilder
     * @throws Exception
     */

    @Test
    public void testHome() throws Exception{
        String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
        Job j = new Job(1234, "test");
        List<Job> job = new ArrayList<Job>();
        job.add(j);
        Project p = new Project(1234,"test","test", new Date(),1234, job, "test");
        List<Project> projects = new ArrayList<Project>();;
        projects.add(p);
        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects.json");
        HashMap<String, String> params = new HashMap<>();
        params.put("query", null);
        params.put("job_details", "true");
        params.put("compact", "false");
        params.put("limit", "10");

        try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
            try (MockedStatic<DescriptionUtil> utilities2 = Mockito.mockStatic(DescriptionUtil.class)) {
                utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache))
                        .thenReturn(json);
                utilities.when(() -> GeneralUtil.getProjectsFromJson(json))
                        .thenReturn(projects);
                utilities2.when(() -> DescriptionUtil.getReadabilityIndex(projects))
                        .thenReturn(projects);

                RequestBuilder request = Helpers.fakeRequest().method(POST).uri("/");
                Result result = route(app, request);
                assertEquals(OK, result.status());

                Http.RequestBuilder request1 = new Http.RequestBuilder()
                        .method(GET)
                        .uri("/");
                request1.session("user","hdalkhdlkjdla");
                Result result1 = route(app, request1);

                Http.RequestBuilder request2 = new Http.RequestBuilder()
                        .method(POST)
                        .uri("/");
                request2.session("user","hdalkhdlkjdla");
                Result result2 = route(app, request2);
                assertEquals(303, result2.status());
            }
        }
    }
    /**
     *  Test method for indiStats
     *  Uses RequestBuilder to make http get request for stats uri
     *  Uses assertEquals to test the response
     */

    @Test
    public void testIndiStats() {
        RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/stats/some%20description&");
        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());

    }

    private WSClient ws;
    private SyncCacheApi cache;

    @Before
    public void injectWsCache() {

        ws = app.injector().instanceOf(WSClient.class);
        cache = app.injector().instanceOf(SyncCacheApi.class);
    }
    /**
     * Test method for globalStats
     * Mocks getJsonResponseFromUrl & getDescriptionFromJson of GeneralUtil class and makes the get request using requestbuilder
     * Uses assertEquals to test the response
     * @throws IOException
     */

    @Test
    public void testGlobalStats() throws Exception {

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

            RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/stats/all/react-native");
            Result result = route(app, request);
            assertEquals(OK, result.status());
            assertEquals("text/html", result.contentType().get());
            assertEquals("utf-8", result.charset().get());
        }
    }
    /**
     * Test method for skills method of HomeController class
     * Mocks getJsonResponseFromUrl of GeneralUtil class and makes the get request using requestbuilder
     * Uses assertEquals to show the correct response
     * @throws IOException
     */

    @Test
    public void testSkills() {
        String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
        HashMap<String, String> params = new HashMap<>();

        try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
            utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, params, ws, cache))
                    .thenReturn("");

            RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/skills/3");
            Result result = route(app, request);
            System.out.println(contentAsString(result));
            assertEquals(OK, result.status());
        }

    }
    /**
     * Test method for User method of HomeController class
     * Mocks getJsonResponseFromUrl and getUserFromJson of GeneralUtil class and makes the get request using requestbuilder
     * Uses assertEquals to show the correct response
     * @throws IOException
     */

    @Test
    public void testUser() throws IOException {
        String url = "https://www.freelancer.com/api/users/0.1/users/32136579";
//        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "User.json");
        User user = new User(32136579, "san6123", "san6123", "employer", 1542651206, false, "employer", "India", true, "Rupee");
        Job j = new Job(1234, "test");
        List<Job> job = new ArrayList<Job>();
        job.add(j);
        Project p = new Project(1234,"test","test", new Date(),1234, job, "test");
        List<Project> projects = new ArrayList<Project>();;
        projects.add(p);
        user.setProjects(projects);
        try (MockedStatic<GeneralUtil> utilities = Mockito.mockStatic(GeneralUtil.class)) {
            try (MockedStatic<UserUtil> utilities2 = Mockito.mockStatic(UserUtil.class)) {

                utilities.when(() -> GeneralUtil.getJsonResponseFromUrl(url, null, ws, cache))
                        .thenReturn(" ");
                utilities2.when(() -> UserUtil.getUserFromJson(" ", ws, cache)).thenReturn(user);
                RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/user/32136579");
                Result result = route(app, request);
                assertEquals(OK, result.status());
            }
        }
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
