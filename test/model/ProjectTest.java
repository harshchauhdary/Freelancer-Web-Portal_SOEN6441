package model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectTest extends TestCase {

    public void testGetSkills() {
        List<Job> j = new ArrayList<>();
        Job j1 = new Job(3,"PHP");
        Job j2 = new Job(17,"Web");
        j.add(j1);
        j.add(j2);
        Project p = new Project();
        p.setSkills(j);
        assertEquals(j,p.getSkills());
    }

    public void testSetSkills() {
        List<Job> j = new ArrayList<>();
        Job j1 = new Job(3,"PHP");
        Job j2 = new Job(17,"Web");
        j.add(j1);
        j.add(j2);
        Project p = new Project();
        p.setSkills(j);
        assertEquals(p.getSkills(),j);

    }

    public void testGetFkglIndex() {
        Project p = new Project();
        p.setFkglIndex(80);
        assertEquals(80,p.getFkglIndex());
        assertTrue(0<p.getFkglIndex() && p.getFkglIndex()<1000);
    }



    public void testGetTimeSubmitted() {
        Project p = new Project();
        Date d = new Date(2011 / 10);
        p.setTimeSubmitted(d);
        assertEquals(d,p.getTimeSubmitted());
    }



    public void testGetTitle() {
        Project p = new Project();
        String title = "react native";
        p.setTitle(title);
        assertEquals(title,p.getTitle());

    }



    public void testGetOwnerID() {
        Project p = new Project();
        long oid = 12345;
        p.setOwnerID(oid);
        assertEquals(oid,p.getOwnerID());
    }




    public void testGetType() {
        Project p = new Project();
        p.setType("fixed");
        assertEquals("fixed",p.getType());
    }

    public void testGetId() {
        Project p = new Project();
        p.setId(12345);
        assertEquals(12345,p.getId());
    }

    public void testGetDesc() {
        Project p = new Project();
        p.setDesc("React Native Developer");
        assertEquals("React Native Developer", p.getDesc());
    }

    public void testGetReadabilityIndex() {
        Project p = new Project();
        p.setReadabilityIndex(70);
        assertEquals(70,p.getReadabilityIndex());
    }

    public void testGetEducationLevel() {

        Date d = new Date();
        Project p = new Project(12345,"abc","react",d,1234,null,"Fixed");
        p.setEducationLevel("5th Grader");
        assertEquals("5th Grader",p.getEducationLevel());
    }
}