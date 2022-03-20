package views;

import model.Job;
import model.Project;
import model.User;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.ws.WSClient;
import play.test.Helpers;
import play.twirl.api.Content;

import java.util.*;

import junit.framework.TestCase;
import scala.App;

import javax.inject.Inject;

import static play.test.Helpers.contentAsString;

public class viewsTest extends TestCase{

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

    public void testError(){
        Content html = views.html.Home.error.render("No projects found");
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains(""));

    }

//    private Form ff;
//    private Messages m;
////    @Before
////    public void injectFormFactory(){
////        Application app = Helpers.fakeApplication();
////        FormFactory ff  = app.injector().instanceOf(FormFactory.class);
////
////    }
//
//    @Inject
//    private FormFactory formFactory;
//    @Before
//    public void injectMessage(){
//        Application app = Helpers.fakeApplication();
//        Messages m = app.injector().instanceOf(Messages.class);
//    }
//    @Test
//    public void testIndex(){
//
//        Application app = Helpers.fakeApplication();
//        FormFactory formFactory  = app.injector().instanceOf(FormFactory.class);
//        Form<Query> f = formFactory.form(Query.class);
//        List<Canva> canva = new ArrayList<Canva>();
//        Canva c = new Canva();
//        List<Project> projects = new ArrayList<Project>();
//        Project p = new Project();
//        Job j = new Job(3,"PHP");
//        List<Job> job = new ArrayList<Job>();
//        job.add(j);
//        p.setEducationLevel("test");
//        p.setDesc("Test");
//        p.setSkills(job);
//        p.setId(12345);
//        p.setReadabilityIndex(12);
//        p.setType("fixed");
//        p.setTitle("test");
//        p.setOwnerID(123456);
//        p.setTimeSubmitted(new Date());
//        p.setFkglIndex(1234);
//        projects.add(p);
//        c.setTitle("Test");
//        c.setAverageIndex(12);
//        c.setProjects(projects);
//        canva.add(c);
//
//        Content html = views.html.Home.index.render(canva,f, m);
//        //List<Project> projects1 = new ArrayList<Project>();
//        assertEquals("text/html", html.contentType());
//        assertTrue(contentAsString(html).contains("projects"));
//
//
//    }

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
