package model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Test class for Project
 */
public class ProjectTest extends TestCase {

    /**
     * Test method to check the getter and setter methods of getSkills in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
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

    /**
     * Test method to check the getter and setter methods of getFglIndex in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testGetFkglIndex() {
        Project p = new Project();
        p.setFkglIndex(80);
        assertEquals(80,p.getFkglIndex());
        assertTrue(0<p.getFkglIndex() && p.getFkglIndex()<1000);
    }


    /**
     * Test method to check the getter and setter methods of getTimeSubmitted in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testGetTimeSubmitted() {
        Project p = new Project();
        Date d = new Date(2011 / 10);
        p.setTimeSubmitted(d);
        assertEquals(d,p.getTimeSubmitted());
    }

    /**
     * Test method to check the getter and setter methods of getTitle in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */

    public void testGetTitle() {
        Project p = new Project();
        String title = "react native";
        p.setTitle(title);
        assertEquals(title,p.getTitle());

    }

    /**
     * Test method to check the getter and setter methods of getOwnerID in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */

    public void testGetOwnerID() {
        Project p = new Project();
        long oid = 12345;
        p.setOwnerID(oid);
        assertEquals(oid,p.getOwnerID());
    }


    /**
     * Test method to check the getter and setter methods of getType in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */

    public void testGetType() {
        Project p = new Project();
        p.setType("fixed");
        assertEquals("fixed",p.getType());
    }

    /**
     * Test method to check the getter and setter methods of getID in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testGetId() {
        Project p = new Project();
        p.setId(12345);
        assertEquals(12345,p.getId());
    }

    /**
     * Test method to check the getter and setter methods of getDesc in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testGetDesc() {
        Project p = new Project();
        p.setDesc("React Native Developer");
        assertEquals("React Native Developer", p.getDesc());
    }

    /**
     * Test method to check the getter and setter methods of getReadabilityIndex in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testGetReadabilityIndex() {
        Project p = new Project();
        p.setReadabilityIndex(70);
        assertEquals(70,p.getReadabilityIndex());
    }

    /**
     * Test method to check the getter and setter methods of getEducationLevel in Project.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */

    public void testGetEducationLevel() {

        Date d = new Date();
        Project p = new Project(12345,"abc","react",d,1234,null,"Fixed");
        p.setEducationLevel("5th Grader");
        assertEquals("5th Grader",p.getEducationLevel());
    }
}