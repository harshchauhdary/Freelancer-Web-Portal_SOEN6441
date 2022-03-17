package Util;

import model.Job;
import  model.Project;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GeneralUtil {




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


    public static Executor getExecutor(){return executor;}

    public static String getJsonResponseFromUrl(String url, HashMap<String, String> params) throws IOException {
        String param = "?";
        if(params!=null)
        {
            for (String key :
                    params.keySet()) {
                param = param + key + "=" + params.get(key) + "&";
            }
        }
        URL obj = new URL(url + param);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

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

    public static User getUserFromJson(String response) throws ParseException, IOException {
        JSONParser parser=new JSONParser();
        JSONObject jObj = (JSONObject) parser.parse(response);
        JSONObject jsonObject=(JSONObject) jObj.get("result");


        String username= (String) jsonObject.get("username");
        long id = (long) jsonObject.get("id");
        String display_name = (String) jsonObject.get("display_name");
        String role = (String) jsonObject.get("role");

        User user_obj=new User(id,username,display_name,role);

        String url="https://www.freelancer.com/api/projects/0.1/projects";
        HashMap<String,String> params = new HashMap<>();
        params.put("owners[]",Long.toString(id));
        params.put("full_description","true");
        params.put("job_details","true");
        params.put("limit","10");
        String data=getJsonResponseFromUrl(url,params);
        List<Project> projects=DescriptionUtil.getReadabilityIndex(getProjectsFromJson(data));
        user_obj.setProjects(projects);

        return user_obj;
    }
}
