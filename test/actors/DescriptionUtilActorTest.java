package actors;

import Util.DescriptionUtil;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import model.Job;
import model.Project;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import play.cache.SyncCacheApi;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class DescriptionUtilActorTest {
    static ActorSystem actorSystem;
    private static TestKit testProbe;

    @Before
    public void setup() {
        actorSystem = ActorSystem.create();
        testProbe = new TestKit(actorSystem);
    }

    @Test
    public void testIndex() {



        new TestKit(actorSystem) {
            {
                List<Project> projects = new ArrayList<>();
                projects.add(new Project(58976525, "Introduction:\\nEmbedded hellet systems UG  is a German Engineering company in Cologne. We are looking for Sen", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed"));

                //        TestProbe<Command> probe =
//                testKit.createTestProbe(Command.class);
//        ActorRef<Command> actor = testKit.spawn(DescriptionUtilActor.create());
//        actor.tell(new DescriptionUtilActor.GetReadabilityIndex(projects, probe.getRef()));
//        Command response = probe.receiveMessage();
                final akka.actor.ActorRef descActor = actorSystem.actorOf(
                        DescriptionUtilActor.props(testProbe.getRef()));
                descActor.tell(new Messages.GetReadabilityIndex(projects), testProbe.getRef());
                Messages.RespondGetReadabilityIndex response = testProbe.expectMsgClass(Messages.RespondGetReadabilityIndex.class);
                int value = response.projects.get(0).getReadabilityIndex();
                int expectedRIndex = 46;

                assertEquals(expectedRIndex, value);
            }
        };


    }
//
//    @Test
//    public void teststop() {
//        ActorRef<Command> actor = testKit.spawn(DescriptionUtilActor.create());
//        actor.tell(PostStop.instance());
//
//    }

    @Test
    public void testAverage() {


        new TestKit(actorSystem) {
            {
                List<Project> projects = new ArrayList<>();
                Project p1 = new Project(58976525, "Introduction:\\nEmbedded hellet systems UG  is a German Engineering company in Cologne. We are looking for Sen", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed");
                p1.setReadabilityIndex(46);
                Project p2 = new Project(58976525, "Introduction:\\nEmbedded hellet systems UG  is a German Engineering company in Cologne. We are looking for Sen", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed");
                p2.setReadabilityIndex(73);
                ActorRef a = null;
                projects.add(p1);
                projects.add(p2);
                //projects.add(new Project(58976525, "I want a professional trader for crypto trading platform. This will be a long term project. The amou", "adw", new Date(), 465656, new ArrayList<Job>(), "fixed"));
                Double expectedResult = Double.valueOf((46 + 73) / 2);
                final akka.actor.ActorRef descActor = actorSystem.actorOf(
                        DescriptionUtilActor.props(a));
                descActor.tell(new Messages.GetAverage(projects), testProbe.getRef());
                Messages.RespondGetAverage response = testProbe.expectMsgClass(Messages.RespondGetAverage.class);
                Double value = response.average;
                assertEquals(expectedResult, value);
            }
        };
    }

    @Test
    public void teste() {
        int[] inputClasses = new int[]{102, 92, 86, 74, 63, 54, 49, 32, 21, 11, -89};
        for (int i = 0; i < 11; i++) {
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

            Assert.assertEquals(expectedEducationLevel, DescriptionUtilActor.getIndexLevel(inputClasses[i]));
        }
    }
}

