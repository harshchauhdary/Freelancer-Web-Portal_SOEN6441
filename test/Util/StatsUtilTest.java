package Util;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

public class StatsUtilTest extends TestCase {

    @Test
    public void testGetStats() {
        Map<String, Long> data = new HashMap<String, Long>()  {{
            put("description", new Long(2));
            put("one", new Long(1));
            put("two", new Long(1));
        }};
        List<String> strings= new ArrayList<String>();
        strings.add("description one");
        strings.add("description two");


        assertEquals(data, StatsUtil.getStats(strings));
    }

    @Test
    public void testSortStats() {
        Map<String, Long> sorted = new LinkedHashMap<String, Long>()  {{
            put("description", new Long(2));
            put("one", new Long(1));
            put("two", new Long(1));
        }};
        Map<String, Long> unsorted = new HashMap<String, Long>()  {{
            put("one", new Long(1));
            put("description", new Long(2));
            put("two", new Long(1));
        }};

        assertEquals(sorted, StatsUtil.sortStats(unsorted));
    }
}