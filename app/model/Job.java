

/**
 @author: Munj Bhavesh Nayak
 This class is utilized in obtaining all the Jobs' names and their respective Job IDs, which are in
 turn stored in JSon File, which is then parsed using Job ID to select the required Jobs.
 */

package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Job {

    private long job_id;
    private String job_name;

    /**
     * Constructor of the Job Class, that initializes and creates the class's objects based on the
     * parameters provided.
     * @param job_id A long Integer which is used to uniquely identify a job
     * @param job_name A String value, which is associated with the unique job ID. Signifies the name of the skill.
     */
    public Job(long job_id, String job_name){
        this.job_id = job_id;
        this.job_name = job_name;
    }

    public Job() {

    }

    /**
     * Getter method used to access the Job_ID of a particular job.
     * @return the job_id with type long
     */

    public long getJob_id() {
        return job_id;
    }

    /**
     * Setter Method to pass the necessary value to the Job_ID
     * @param job_id A long integer used to uniquely identify the Jobs
     */
    public void setJob_id(long job_id) {
        this.job_id = job_id;
    }

    /**
     * Getter Method, used to access the Job Name.
     * @return the Job name of the type String
     */
    public String getJob_name() {
        return job_name;
    }

    /**
     * Setter Method, used to pass the necessary value to the Job Name
     * @param job_name A String which contains the name associated with the unique Job ID
     */

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }



}
