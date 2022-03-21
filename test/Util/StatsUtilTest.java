package Util;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

/**
 * Test cases for class statsUtil
 * @author Harsh 40201627
 */
public class StatsUtilTest extends TestCase {

    /**
     * Test case for StatsUtil.getStats method.It has 2 classes(List of descriptions and an empty arraylist).
     * Checks if it returns correct statistics.
     */
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

    /**
     * Test case for StatsUtil.sortStats method. Passes an unsorted Map and checks
     * if it returns a sorted LinkedHashMap.
     */
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