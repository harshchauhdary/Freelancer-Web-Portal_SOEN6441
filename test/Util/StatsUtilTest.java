package Util;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

public class StatsUtilTest extends TestCase {

    @Test
    public void testGetStats() {
        Map<String, Long> stats = new HashMap<String, Long>()  {{
            put("description", new Long(2));
            put("one", new Long(1));
            put("two", new Long(1));
        }};
        List<String> descriptions= new ArrayList<String>();
        descriptions.add("description .;one");
        descriptions.add("description % two");
        assertEquals(stats, StatsUtil.getStats(descriptions));

        List<String> empty_descriptions= new ArrayList<String>();
        Map<String, Long> empty_stats = new HashMap<String, Long>();
        assertEquals(empty_stats, StatsUtil.getStats(empty_descriptions));
    }

    @Test
    public void testSortStats() {
        LinkedHashMap<String, Long> sorted = new LinkedHashMap<String, Long>()  {{
            put("description", new Long(457));
            put("one", new Long(54));
            put("two", new Long(23));
        }};
        Map<String, Long> unsorted = new HashMap<String, Long>()  {{
            put("one", new Long(54));
            put("description", new Long(457));
            put("two", new Long(23));
        }};

        assertEquals(sorted, StatsUtil.sortStats(unsorted));
    }
}