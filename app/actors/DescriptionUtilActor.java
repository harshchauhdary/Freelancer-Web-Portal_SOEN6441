package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.pattern.StatusReply;
import model.Project;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DescriptionUtilActor extends AbstractActor {

    public DescriptionUtilActor(ActorRef a) {

    }

    public static Props props(ActorRef a) {
        return Props.create(DescriptionUtilActor.class,a);
    }
    @Override
    public void preStart() {
        System.out.println("Description actor created.");
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.GetReadabilityIndex.class, rIndex -> {
                    onGetReadabilityIndex(rIndex);
                }).match(Messages.GetAverage.class, rAvg -> {
                    onGetAverage(rAvg);
                })
                .build();
    }
        private void onGetReadabilityIndex(Messages.GetReadabilityIndex gri){

            List<Project> projects1 = gri.projects.stream().map(p -> {
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
            }).collect(Collectors.toList());

            getSender().tell(new Messages.RespondGetReadabilityIndex(projects1), getSelf());
            //tell here
        }

        private void onGetAverage(Messages.GetAverage g){

            List<Project> projects = g.projects;
            double result;
            if(projects.size() == 0){
                result = Double.valueOf(0);
            }
            Optional<Integer> sum = projects.stream().map(p -> p.getReadabilityIndex()).reduce(Integer::sum);

            result = (double) ((int) sum.get() / projects.size());
            getSender().tell(new Messages.RespondGetAverage(result), getSelf());

        }

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



}