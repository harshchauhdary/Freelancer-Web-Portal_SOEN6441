package views;

import model.*;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;

import play.mvc.Http;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;

import java.util.*;



import javax.inject.Inject;

import static junit.framework.TestCase.*;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;

public class viewsTest extends WithApplication {
    /**
     * Test method for skills.scala.html
     * Passes a project list while rendering the page.
     * assertEquals is used to check the contentType of the page
     * assertTrue is used to check that the page is rendered properly.
     */
    @Test
    public void testSkills(){
        List<Project> projects = new ArrayList<Project>();
        Project p = new Project();
        Job j = new Job(3,"PHP");
        List<Job> job = new ArrayList<Job>();
        job.add(j);
        p.setEducationLevel("test");
        p.setDesc("Test");
        p.setSkills(job);
        p.setId(12345);
        p.setReadabilityIndex(12);
        p.setType("fixed");
        p.setTitle("test");
        p.setOwnerID(123456);
        p.setTimeSubmitted(new Date());
        p.setFkglIndex(1234);
        projects.add(p);


        Content html = views.html.Home.skills.render(projects,Double.valueOf(1));
        List<Project> projects1 = new ArrayList<Project>();
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("projects"));


        Content html1 = views.html.Home.skills.render(projects1,Double.valueOf(10));
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("projects"));

    }
    @Test
    /**
     * Test method to check the error.scala.html.
     * A String argument is passed as error message.
     * assertEquals is used to check the contentType of the page
     * assertTrue is used to check the page rendering
     */
    public void testError(){
        Content html = views.html.Home.error.render("No projects found");
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains(""));

    }

    private FormFactory ff;
    private MessagesApi m;

    /**
     * Injection method to inject formFactory
     */
    @Before
    public void injectFormFactory(){

         ff  = app.injector().instanceOf(FormFactory.class);
        m = app.injector().instanceOf(MessagesApi.class);

    }


    /**
     * Test method for index.scala.html
     * Passes a canva list, form, and play i18 message while rendering the page.
     * assertEquals is used to check the contentType of the page
     * assertTrue is used to check that the page is rendered properly.
     * fakeRequest is used for mocking purposes.
     */
    @Test
    public void testIndex(){

        Application app = Helpers.fakeApplication();
        FormFactory formFactory  = app.injector().instanceOf(FormFactory.class);
        Form<Query> f = formFactory.form(Query.class);
        List<Canva> canva = new ArrayList<Canva>();
        Canva c = new Canva();
        List<Project> projects = new ArrayList<Project>();
        Project p = new Project();
        Job j = new Job(3,"PHP");
        List<Job> job = new ArrayList<Job>();
        job.add(j);
        p.setEducationLevel("test");
        p.setDesc("Test");
        p.setSkills(job);
        p.setId(12345);
        p.setReadabilityIndex(12);
        p.setType("fixed");
        p.setTitle("test");
        p.setOwnerID(123456);
        p.setTimeSubmitted(new Date());
        p.setFkglIndex(1234);
        projects.add(p);
        c.setTitle("Test");
        c.setAverageIndex(12);
        c.setProjects(projects);
        canva.add(c);
        Http.RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/");
        Content html = views.html.Home.index.render(canva,f, m.preferred(request.build()));
        //List<Project> projects1 = new ArrayList<Project>();
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("projects"));


    }
    /**
     * Test method for user.scala.html
     * Passes a user object while rendering the page.
     * assertEquals is used to check the contentType of the page
     * assertTrue is used to check that the page is rendered properly.
     */
    @Test
    public void testUser(){
        User u=new User();
       List<Project> projects = new ArrayList<Project>();
       List<Job> jobs=new ArrayList<>();
       Job j = new Job(3,"PHP");
       jobs.add(j);
       Project p = new Project(1234,"abcd","xyz", new Date(), 1234, jobs,"fixed" );
       projects.add(p);
        u.setId(1234);
        u.setUsername("abcd");
        u.setDisplay_name("abcd");
        u.setRole("employer");
        u.setChosen_role("employer");
        u.setEmail_verified(true);
        u.setLimited_account(false);
        u.setPrimary_currency("Rupee");
        u.setReg_date(12345);
        u.setProjects(projects);
       Content html1 = views.html.Home.user.render(u);
       assertEquals("text/html", html1.contentType());
       assertTrue(contentAsString(html1).contains("User"));

    }
    /**
     * Test method for stats.scala.html
     * Passes a linked hashmap with keys of type String and values of type Long while rendering the page.
     * assertEquals is used to test the contentType of the page
     * assertTrue is used to verify the content
     */
    @Test
    public void testStats(){
        LinkedHashMap<String, Long> stats = new LinkedHashMap<String, Long>()  {{
            put("description", new Long(457));
            put("one", new Long(54));
            put("two", new Long(23));
        }};
        Content html = views.html.Home.stats.render(stats);
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("Word Statistics"));
    }
}
