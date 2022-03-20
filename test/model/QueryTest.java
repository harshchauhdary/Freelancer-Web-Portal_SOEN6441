package model;

import junit.framework.TestCase;

public class QueryTest extends TestCase {

    public void testGetQuery() {
        Query q = new Query();
        q.setQuery("react native");
        assertEquals(q.getQuery(), "react native");
    }
}