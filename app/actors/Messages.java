package actors;

import Util.Command;
import com.fasterxml.jackson.databind.JsonNode;
import model.*;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;

import java.util.LinkedHashMap;
import java.util.List;

public class Messages {
    public interface command{}

    public static final class GetStats implements Command {
        String query;

        /**
         * Constructor for the protocol
         * @param query search query
         * @author Harsh
         */
        public GetStats(String query) {
            this.query = query;
        }
    }

    public static final class GetSearchResult implements Command{
        String query;
        public GetSearchResult(String query){
            this.query = query;
        }
    }
    public static final class GetSkills {
        String jobId;
        public GetSkills(String jobId) {
            this.jobId = jobId;
            //  this.replyTo = replyTo;
        }

    }

    public static final class GetUser implements Command {
        String id;
        public GetUser(String id){
            this.id=id;
        }
    }

    public static final class SkillsDetails  {
        public final JsonNode skillsDetails;

        public SkillsDetails(JsonNode skillsDetails) {
            this.skillsDetails = skillsDetails;
        }
    }
    public static final class UserDetails  {
        public final JsonNode user;

        public UserDetails(JsonNode user) {
            this.user = user;
        }
    }

    public static final class RespondGetReadabilityIndex {
        public List<Project> projects;
        public RespondGetReadabilityIndex(List<Project> projects) {
            this.projects = projects;
        }
    }

    public static final class RespondGetAverage implements Command {
        public Double average;
        public RespondGetAverage(Double average) {
            this.average = average;
        }
    }

    public static final class RespondGetUser implements Command{
        public User user;
        public RespondGetUser(User user){
            this.user=user;
        }
    }

    public static final class GetReadabilityIndex  {
        List<Project> projects;
        //ActorRef replyTo;
        public GetReadabilityIndex(List<Project> projects) {
            this.projects = projects;
            //  this.replyTo = replyTo;
        }
    }
    public static final class GetAverage  {
        List<Project> projects;
        //ActorRef replyTo;
        public GetAverage(List<Project> projects) {
            this.projects = projects;
            //this.replyTo = replyTo;
        }
    }


    /**
     * Protocol to get description for an individual project stats
     */
    public static final class GetIndiStats implements Command {
        String description;

        /**
         * Constructor for GetIndiStats
         * @param description string containing a project preview_description
         * @author Harsh
         */
        public GetIndiStats(String description) {
            this.description = description;
        }
    }


    public static final class RespondStats {
        public final JsonNode stats;

        public RespondStats(JsonNode stats) {
            this.stats = stats;
        }
    }

    public static final class RespondSearch {
        public final JsonNode searchResult;

        public RespondSearch(JsonNode searchResult) {
            this.searchResult = searchResult;
        }
    }
}
