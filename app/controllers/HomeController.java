package controllers;

import Util.DescriptionUtil;
import model.Project;
import model.User;
import org.json.simple.parser.ParseException;
import play.mvc.*;
import Util.GeneralUtil;
import Util.StatsUtil;
import java.util.*;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public Result index() {

        return ok(views.html.Home.home.render());
    }
    
    public Result skills(String jobId) throws IOException, ParseException, ExecutionException, InterruptedException {
        String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
        HashMap<String,String> params = new HashMap<>();
        params.put("job_details","true");
        params.put("compact","false");
        params.put("full_description","true");
        params.put("jobs[]",jobId);
        params.put("limit","10");

        String jsonResponse = GeneralUtil.getJsonResponseFromUrl(url,params);

        List<Project> projects = DescriptionUtil.getReadabilityIndex(GeneralUtil.getProjectsFromJson(jsonResponse));
        float averageIndex = DescriptionUtil.getAverageReadabilityIndex(projects);
        return ok(views.html.Home.skills.render(projects,averageIndex));


    }
    public Result user(String id) throws IOException, ParseException {
        String url="https://www.freelancer.com/api/users/0.1/users/"+id;
        String jsonRespone= GeneralUtil.getJsonResponseFromUrl(url,null);
        User user = GeneralUtil.getUserFromJson(jsonRespone);
        return ok(views.html.Home.user.render(user));
    }

    public Result stats(String query, String single) throws IOException, ParseException {
        List<String> result = new ArrayList<>();
        if(single.equals("false")) {
            String encodeQuery = java.net.URLEncoder.encode(query, "UTF-8");

            String url = "https://www.freelancer.com/api/projects/0.1/projects/all/";
            HashMap<String, String> params = new HashMap<>();
            params.put("query", encodeQuery);
            params.put("sort_field", "time_updated");
            params.put("compact", "false");
            params.put("full_description", "true");
            params.put("offset", "0");
            String response1 = GeneralUtil.getJsonResponseFromUrl(url, params);
            params.remove("offset");
            params.put("offset", "100");
            String response2 = GeneralUtil.getJsonResponseFromUrl(url, params);
            params.remove("offset");
            params.put("offset", "200");
            params.put("limit", "50");
            String response3 = GeneralUtil.getJsonResponseFromUrl(url, params);
            result.addAll(GeneralUtil.getDescriptionFromJson(response1));
            result.addAll(GeneralUtil.getDescriptionFromJson(response2));
            result.addAll(GeneralUtil.getDescriptionFromJson(response3));
        }
        else{
            result.add(query);
        }
        Map<String, Long> stats = StatsUtil.getStats(result);
        return ok(views.html.Home.stats.render(StatsUtil.sortStats(stats),query));
    }

}
