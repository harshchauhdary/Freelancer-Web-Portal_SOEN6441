package model;

import junit.framework.TestCase;

public class QueryTest extends TestCase {

    /**
     * Test method to check the getter and setter methods of Query.java model
     * uses the setter to pass an argument and compares it with actual value using getter, through assertEquals
     */
    public void testGetQuery() {
        Query q = new Query();
        q.setQuery("react native");
        assertEquals(q.getQuery(), "react native");
    }
}