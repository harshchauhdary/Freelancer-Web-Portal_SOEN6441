package Util;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.regex.*;

public class StatsUtil {
    public static Map<String, Long> getStats(List<String> disc_list){
      Stream<String> a = disc_list.stream().flatMap(d -> {
          List<String> w = new ArrayList<>();
          Matcher matcher
                  = Pattern.compile("(\\w+)")
                  .matcher(d);
          while (matcher.find()) {
              // Get the group matched using group() method
              w.add(matcher.group());
          }
          return w.stream();
      });

       Map<String, Long> data = a.collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ));

       return data;

    }

    public static LinkedHashMap<String, Long> sortStats(Map<String, Long> stats){
        LinkedHashMap<String, Long> sortedStats = new LinkedHashMap<>();

        stats.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedStats.put(x.getKey(), x.getValue()));

        return sortedStats;
    }
}