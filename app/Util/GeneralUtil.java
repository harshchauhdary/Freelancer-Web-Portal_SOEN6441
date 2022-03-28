package Util;
import com.fasterxml.jackson.databind.JsonNode;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;
import model.Job;
import  model.Project;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import play.libs.ws.WSResponse;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Contains all general methods used for fetching and processing the response
 * @author Sahil Munj Bhargav Harsh
 */
public class GeneralUtil {

    /**
     * Executor used by the completable future defining the number of threads i.e.250
     */
    private final static Executor executor ;

    static{
        executor =  Executors.newFixedThreadPool(250,
                (Runnable r) -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
        );
    }

    /**
     * Gets the custom Executor created in the static block.
     * @return Executor
     */
    public static Executor getExecutor(){return executor;}

    /**
     * Gets the json String using the url and params passed.It makes  asynchronous call to the freelance api with the url constructed and
     * returns the json response fetched from the api.It uses ws service of plat framework for the asynchronus call
     * @author Sahil_40192697
     * @param url base url
     * @param params parameter to be added to the url
     * @param ws Wsclient used for sending the request asynchronous
     * @return json response from the api
     * @throws IOException
     * @see <a href="https://www.google.com/url?q=https://www.freelancer.com/api&sa=D&source=editors&ust=1647564305189643&usg=AOvVaw1Hch_j-vbGsnR5Jyo4-TK8">Freelancer api<a/>
     * @see <a href="https://www.playframework.com/documentation/2.8.x/ScalaWS">Play Ws<a/>
     */
    public static String getJsonResponseFromUrl(String url, HashMap<String, String> params, WSClient ws,SyncCacheApi cache) throws IOException, ExecutionException, InterruptedException {
        String param = "?";
        String fullURl;
        if(params!=null)
        {
            for (String key :
                    params.keySet()) {
                param = param + key + "=" + params.get(key) + "&";
            }

            fullURl = url + param;
        }
        else {
            fullURl = url;
        }
        Optional<String> r = cache.get(fullURl);
        if(r.isPresent()){
           return r.get();
        }

        CompletableFuture<JsonNode> response = ws.url(fullURl)
                .get().thenApply(WSResponse::asJson).toCompletableFuture();
        String re = response.get().toString();
        cache.set(fullURl , re);
        return re;
    }

    /**
     * Used to obtain the list of projects from the received JSON file.
     * This is done by parsing the JSON, storing data in JSONArrays.
     * The Arrays are then traversed and the required data (Job Name and Job ID) are collected into a List.
     * Completable Future and Java 8+ Streams are used to ensure non-blocking and Asynchronous execution of the code.
     * @param response Entire JSON file received from the getJSONFromURL method in the String format.
     * @return A list of projects, linked together in the key-value pairs of Job ID and Job Name
     * @throws ParseException It is encountered when the system encounters a runtime error while traversing
     */

    public static List<Project> getProjectsFromJson(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(response);
        JSONObject dataMap = (JSONObject) jObj.get("result");

        JSONArray dataArray = (JSONArray) dataMap.get("projects");


        List<CompletableFuture<Project>> collect = (List<CompletableFuture<Project>>) dataArray.stream().map(obj -> CompletableFuture.supplyAsync(() -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    long id = (long) jsonObject.get("owner_id");
                    String description = (String) jsonObject.get("preview_description");
                    String title = (String) jsonObject.get("title");
            List<Job> skills = new ArrayList<>();
                    JSONArray jobArray = (JSONArray) jsonObject.get("jobs");
                    for(int i=0;i<jobArray.size();i++) {
                        JSONObject jsonObjectOne = (JSONObject) jobArray.get(i);
                        long job_id = (long) jsonObjectOne.get("id");
                        String job_name = (String) jsonObjectOne.get("name");
                        skills.add(new Job(job_id, job_name));
                    }
                    long ownerID = (long) jsonObject.get("owner_id");
                    long timeSubmitted = (long) jsonObject.get("time_submitted");
                    String type = (String) jsonObject.get("type");
                    Date time_submitted = new Date(timeSubmitted * 1000);
                    return new Project(id, description, title, time_submitted, ownerID, skills, type);
                }, GeneralUtil.executor)
        ).collect(Collectors.toList());

        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());

    }

    /**
     * Extracts preview_descriptions form a given JSON response.
     * @author Harsh 40201627
     * @param response JSON response received from the api.
     * @return returns a List of Strings containing preview descriptions.
     * @throws ParseException
     * @see Project
     */
    public static List<String> getDescriptionFromJson(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(response);
        JSONObject dataMap = (JSONObject) jObj.get("result");

        JSONArray dataArray = (JSONArray) dataMap.get("projects");


        List<CompletableFuture<String>> collect = (List<CompletableFuture<String>>) dataArray.stream().map(obj -> CompletableFuture.supplyAsync(() -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    String lcase = (String) jsonObject.get("preview_description");
                    return lcase.toLowerCase();
                }, GeneralUtil.executor)
        ).collect(Collectors.toList());

        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * Used to create a String id which is unique to every browsers.It takes the set of all id uptil now and then genera
     * -tes the id using the random.
     * @param ids set of unique ids of browsers
     * @return Unique id generated for browser.
     * @author Sahil_40192697
     */
    public static String generateId(Set<String> ids){
        boolean check = true;

        String ch = "ABCDEFHIJKKLMNPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder id = new StringBuilder();
        while(check) {
            for(int i = 0 ; i<10;i++) {
                int index = (int) (ch.length() * Math.random());
                id.append(ch.charAt(index));
            }
            check = ids.stream().anyMatch(r -> r.equals(id.toString()));
        }
        return id.toString();
    }
}
