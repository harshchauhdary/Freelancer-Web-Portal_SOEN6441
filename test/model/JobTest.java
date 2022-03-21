package model;

import junit.framework.TestCase;

import java.lang.reflect.Field;

public class JobTest extends TestCase {

    /**
     * Test method to check the getter methods of job_id in Job.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     * Integrity is ensured using assertFalse
     */
    public void testGetJob_id() throws NoSuchFieldException, IllegalAccessException {
        final Job j = new Job(3,"PHP");
        j.setJob_id(3);
        final Field field = j.getClass().getDeclaredField("job_id");
        field.setAccessible(true);
        assertEquals(j.getJob_id(),3);
        assertFalse(j.getJob_id()<0);
        assertFalse(j.getJob_id()>999);
        assertTrue(j.getJob_id()>0 && j.getJob_id()<1000);
    }
    /**
     * Test method to check the setter methods of job_id in Job.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     * Integrity is ensured using assertFalse
     */
    public void testSetJob_id() {
        final Job j = new Job();
        j.setJob_id(3);
        assertEquals(j.getJob_id(),3);
        assertEquals(j.getJob_id(),3);
        assertFalse(j.getJob_id()<0);
        assertFalse(j.getJob_id()>999);
        assertTrue(j.getJob_id()>0 && j.getJob_id()<1000);

    }
    /**
     * Test method to check the getter methods of job_name in Job.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testGetJob_name() throws NoSuchFieldException {
        final Job j = new Job(3,"PHP");
        j.setJob_name("PHP");
        final Field field = j.getClass().getDeclaredField("job_id");
        field.setAccessible(true);
        assertEquals(j.getJob_name(),"PHP");
    }

    /**
     * Test method to check the setter methods of job_name in Job.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testSetJob_name() {
        final Job j = new Job();
        j.setJob_name("PHP");
        assertEquals(j.getJob_name(),"PHP");
    }
}