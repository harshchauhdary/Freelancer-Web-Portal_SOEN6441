package Util;
import model.Project;

import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.regex.*;

/**
 * Utility functions for displaying the word statistics of the 250 latest projects for a given query as well as for
 * individual projects.
 * @author Harsh 40201627
 */
public class StatsUtil {
    /**
     * Calculates word statistics from a list of strings.
     * @param disc_list List of preview descriptions of the projects.
     * @return Returns a Map&lt;String, Long&gt; containing words as keys and their frequency as values
     * @see Project
     */
    public static Map<String, Long> getStats(List<String> disc_list){
        // getting words using stream, regex and matcher
        Stream<String> a = disc_list.stream().flatMap(d -> {
            List<String> w = new ArrayList<>();
            Matcher matcher
                    = Pattern.compile("(\\w+)")
                    .matcher(d);
            while (matcher.find()) {
                // get the group matched using group() method
                w.add(matcher.group());
            }
            return w.stream();
        });

        // calculating frequency of each word and storing in a map
        Map<String, Long> data = a.collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ));

        return data;

    }

    /**
     * Sorts map by value in descending order using LinkedHashMap(as it stores in same order as insertion)
     * @param stats Map&lt;String, Long&gt; containing words as keys and their frequency as values
     * @return Returns a LinkedHashMap&lt;String, Long&gt; containing sorted statistics
     */
    public static LinkedHashMap<String, Long> sortStats(Map<String, Long> stats){
        LinkedHashMap<String, Long> sortedStats = new LinkedHashMap<>();

        //sorting and adding to the LinkedHashMap
        stats.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedStats.put(x.getKey(), x.getValue()));

        return sortedStats;
    }
}