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
 *
 * @author Sahil
 */
public class DescriptionUtil {
    /**
     * It computes the readability index and FkGL of all project description and then sets the property "readability index" and "FKGL"of Project with it.
     *
     * @param projects List of all project for whom the readability index has to be calculated.
     * @return List of projects for whom the readability index is set as a property.
     * @author Sahil
     * @see <a href="https://www.google.com/url?q=http://users.csc.calpoly.edu/~jdalbey/305/Projects/FleschReadabilityProject.html&sa=D&source=editors&ust=1647564305219750&usg=AOvVaw0IOgagGnM1UmYzi7T4jVRa">Flesch Readability Index</a>
     * @see <a href="https://www.google.com/url?q=https://en.wikipedia.org/wiki/Flesch%25E2%2580%2593Kincaid_readability_tests&sa=D&source=editors&ust=1647564305226163&usg=AOvVaw3bwQ9Dl_E-VdqhapgkmnBC">FKGL</a>
     * @see Project
     */
    public static List<Project> getReadabilityIndex(List<Project> projects) {

        List<CompletableFuture<Project>> result = projects.stream().map(p -> CompletableFuture.supplyAsync(() -> {

            double sentences = p.getDesc().split("[.!?:;]+").length;
            String[] w = p.getDesc().split("[ .!?;:\\s]+");
            double words = w.length;
            double syllables = 0;
            Pattern pattern = Pattern.compile("[aeiouyAEIOUY]+");
            for (int i = 0; i < w.length; i++) {
                Matcher matcher = pattern.matcher(w[i]);
                while (matcher.find()) {

                    if (w[i].length() <= 3) {
                        syllables++;
                        break;
                    }
                    if (matcher.group().equals("e")) {
                        if (matcher.end() == w[i].length()) {
                            continue;
                        }
                        if (matcher.end() == (w[i].length() - 1)) {
                            if (!(w[i].endsWith("es") || w[i].endsWith("ed"))) {
                                syllables++;
                            }
                            continue;
                        }
                    }
                    syllables++;
                }
            }


            int index = (int) (206.835 - (84.6 * (syllables / words)) - (1.015 * (words / sentences)));
            int fkgl = (int) (-15.59 + (11.8 * (syllables / words)) + (0.39 * (words / sentences)));
            p.setReadabilityIndex(index);
            p.setFkglIndex(fkgl);
            p.setEducationLevel(getIndexLevel(index));

            return p;
        }, GeneralUtil.getExecutor())).collect(Collectors.toList());

        return result.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * It returns the level of education required for a particular person to understand the description of project based on the readability index.
     *
     * @param fleschIndex readability index of the description
     * @return Education level required based on the readability index.
     * @author Sahil
     * @see <a href="https://www.google.com/url?q=http://users.csc.calpoly.edu/~jdalbey/305/Projects/FleschReadabilityProject.html&sa=D&source=editors&ust=1647564305219750&usg=AOvVaw0IOgagGnM1UmYzi7T4jVRa">Flesch Readability Index</a>
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
        } else if (fleschIndex > 61) {
            educationLevel = "8th grader";
        } else if (fleschIndex > 51) {
            educationLevel = "9th grader";
        } else if (fleschIndex > 41) {
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
     *
     * @param projects list of projects who have their readability index set
     * @return average of all the readability index as a float value
     * @throws ExecutionException
     * @throws InterruptedException
     * @author Sahil
     * @see <a href="https://www.google.com/url?q=http://users.csc.calpoly.edu/~jdalbey/305/Projects/FleschReadabilityProject.html&sa=D&source=editors&ust=1647564305219750&usg=AOvVaw0IOgagGnM1UmYzi7T4jVRa">Flesch Readability Index</a>
     */
    public static Double getAverageReadabilityIndex(List<Project> projects) throws ExecutionException, InterruptedException {
        if(projects.size() == 0){
            return Double.valueOf(0);
        }
        CompletableFuture<Optional<Integer>> sum = CompletableFuture.supplyAsync(() -> {
            return projects.stream().map(p -> p.getReadabilityIndex()).reduce(Integer::sum);
        }, GeneralUtil.getExecutor());
        Optional<Integer> s = sum.get();

        return (double) ((int) s.get() / projects.size());
    }


}
