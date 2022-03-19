package Util;
import static org.junit.Assert.*;

import jdk.nashorn.internal.runtime.PrototypeObject;
import model.Job;
import model.Project;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DescriptionUtilTest {


    @Test
    public void getIndexLevelTest(){
        int[] inputClasses = new int[]{102,92,86,74,63,54,49,32,21,11,-89};
       for(int i =0 ;i < 11;i++){
           String expectedEducationLevel;
           if (inputClasses[i] > 100) {
               expectedEducationLevel = "4th grader";
           } else if (inputClasses[i] > 91) {
               expectedEducationLevel = "5th grader";
           } else if (inputClasses[i] > 81) {
               expectedEducationLevel = "6th grader";
           } else if (inputClasses[i] > 71) {
               expectedEducationLevel = "7th grader";
           } else if (inputClasses[i] > 61) {
               expectedEducationLevel = "8th grader";
           } else if (inputClasses[i] > 51) {
               expectedEducationLevel = "9th grader";
           } else if (inputClasses[i] > 41) {
               expectedEducationLevel = "high school graduate";
           } else if (inputClasses[i] > 31) {
               expectedEducationLevel = "Some college";
           } else if (inputClasses[i] > 0) {
               expectedEducationLevel = "college graduate";
           } else {
               expectedEducationLevel = "law School graduate";
           }

           assertEquals(expectedEducationLevel,DescriptionUtil.getIndexLevel(inputClasses[i]));
       }

    }

    @Test
    public void getReadabilityIndexTest(){
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(58976525, "Introduction:\\nEmbedded hellet systems UG  is a German Engineering company in Cologne. We are looking for Sen", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed"));
        List<Project> result = DescriptionUtil.getReadabilityIndex(projects);
        int expectedRIndex = 46;
        int expectedFkgl = 8;
        String expectedLevel = "high school graduate";
        assertEquals(expectedRIndex,result.get(0).getReadabilityIndex());
        assertEquals(expectedFkgl,result.get(0).getFkglIndex());
        assertEquals(expectedLevel,result.get(0).getEducationLevel());
    }


    @Test
    public void getAverageReadabilityIndexTest() throws ExecutionException, InterruptedException {
        List<Project> projects = new ArrayList<>();
        List<Project> projects1 = new ArrayList<>();
        projects.add(new Project(58976525, "Introduction:\\nEmbedded hellet systems UG  is a German Engineering company in Cologne. We are looking for Sen", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed"));
        projects.add(new Project(58976525, "I want a professional trader for crypto trading platform. This will be a long term project. The amou", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed"));
        List<Project> result = DescriptionUtil.getReadabilityIndex(projects);
        List<Project> result1 = DescriptionUtil.getReadabilityIndex(projects1);

        Double expectedResult = Double.valueOf((46 + 73)/2);
        Double expectedResult1 = Double.valueOf(0);

        assertEquals(expectedResult,DescriptionUtil.getAverageReadabilityIndex(result));


        assertEquals(expectedResult1,DescriptionUtil.getAverageReadabilityIndex(result1));

    }
}
