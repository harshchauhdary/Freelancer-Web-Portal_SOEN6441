package Util;

import model.Project;
import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import play.cache.SyncCacheApi;
import play.libs.ws.WSClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static Util.GeneralUtil.getJsonResponseFromUrl;
import static Util.GeneralUtil.getProjectsFromJson;

public class UserUtil {
    /**
     * This method provides user data which is obtained through response parameter, response parameter contains data which obtained directly from url
     * @author Bhargav Bhutwala 40196468
     * @param response user data in JSON form
     * @param ws Wsclient to make a asynchronous request
     * @return user object containing last 10 projects of user and user details like id, username...
     * @throws ParseException Signals that an error has been reached unexpectedly while parsing
     * @throws IOException thrown when an I/O error occurs
     * @see User
     * @see Project
     */

    public static User getUserFromJson(String response, WSClient ws, SyncCacheApi cache) throws ParseException, IOException, ExecutionException, InterruptedException {
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
        String data=getJsonResponseFromUrl(url,params,ws,cache);
        List<Project> projects=DescriptionUtil.getReadabilityIndex(getProjectsFromJson(data));
        user_obj.setProjects(projects);

        return user_obj;
    }
}
