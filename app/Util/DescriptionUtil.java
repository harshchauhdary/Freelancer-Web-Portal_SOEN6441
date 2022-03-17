package Util;

import model.Project;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility functions for computing the readability and average of all the readability index of all projects.
 * @author S_K
 * @version 1.5
 */
public class DescriptionUtil {
    /**
     * It computes the readability index of all project description and then sets the property f readability index with it.
     * @param projects List of all project for whom the readability index has t be calculated.
     * @return List of projects for whom the readability index is set as a property.
     */
    public static List<Project> getReadabilityIndex(List<Project> projects) {

        List<CompletableFuture<Project>> result = projects.stream().map(p -> CompletableFuture.supplyAsync(() -> {

            int sentences = p.getDesc().split("[.!?:;]").length;
            String[] w = p.getDesc().split("[ .!?;:]");
            int words = w.length;
            int syllables = 0;
            Pattern pattern = Pattern.compile("[aeiouAEIOU]+");
            for (int i = 0; i < w.length; i++) {
                Matcher matcher = pattern.matcher(w[i]);
                if (w[i].length() <= 3 && matcher.find()) {
                    syllables++;
                } else {
                    while (matcher.find()) {
                        if (matcher.group().equals("e")) {
                            if (matcher.end() == w[i].length()) {
                                continue;
                            }
                            if (matcher.end() == w[i].length() - 1) {
                                if (!(w[i].endsWith("es") || w[i].endsWith("ed"))) {
                                    syllables++;
                                }
                                continue;
                            }
                        }
                        syllables++;
                    }
                }
            }

            int index = (int) (206.835 - (84.6 * (syllables / words)) -( 1.015 * (words / sentences)));
            int fkgl = (int) (-15.59 + (11.8 * (syllables / words)) + (0.39 * (words / sentences)));
            p.setReadabilityIndex(index);
            p.setFkglIndex(fkgl);
            p.setEducationLevel(getIndexLevel(index));

            return p;
        },GeneralUtil.getExecutor())).collect(Collectors.toList());

        return result.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * It returns the level of education required for a particular person to understand the description of project based on the readability index.
     * @param fleschIndex readability index of the description
     * @return Education level required based on the readability index.
     */
    public static String getIndexLevel(int fleschIndex) {
        String educationLevel;
        if (fleschIndex > 100) {
            educationLevel = "4th grader";
        } else if (fleschIndex > 91) {
            educationLevel = "5th grader";
        } else if (fleschIndex > 81) {
            educationLevel = "6th grader";
        } else if (fleschIndex > 71) {
            educationLevel = "7th grader";
        } else if (fleschIndex > 66) {
            educationLevel = "8th grader";
        } else if (fleschIndex > 61) {
            educationLevel = "9th grader";
        } else if (fleschIndex > 51) {
            educationLevel = "high school graduate";
        } else if (fleschIndex > 31) {
            educationLevel = "Some college";
        } else if (fleschIndex > 0) {
            educationLevel = "college graduate";
        } else {
            educationLevel = "law School graduate";
        }
        return educationLevel;
    }

    /**
     * It returns the average readability index of all the readability index of the projects passed as  argument
     * @param projects list of projects who have their readability index set
     * @return average of all the readability index as a float value
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static float getAverageReadabilityIndex(List<Project> projects) throws ExecutionException, InterruptedException {
        CompletableFuture<Optional<Integer>> sum = CompletableFuture.supplyAsync(() -> {
            return projects.stream().map(p -> p.getReadabilityIndex()).reduce(Integer::sum);
        },GeneralUtil.getExecutor());
        Optional<Integer> s = sum.get();
        return ((int) s.get() / projects.size());
    }


}
