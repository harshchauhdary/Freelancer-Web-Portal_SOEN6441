package controllers;

import Util.DescriptionUtil;
import model.Canva;
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

    public Result index(){
        List<Canva> canvas = new ArrayList<>();
        canvas.addAll(Canva.getCanvas());
        Collections.reverse(canvas);
        return ok(views.html.Home.index.render(canvas));
    }

    public Result home(String query) throws IOException, ExecutionException, InterruptedException, ParseException {




            String url = "https://www.freelancer.com/api/projects/0.1/projects/active/";
            HashMap<String, String> params = new HashMap<>();
            params.put("job_details", "true");
            params.put("compact", "false");
            params.put("full_description", "true");
            params.put("limit", "10");

            String jsonResponse = GeneralUtil.getJsonResponseFromUrl(url, params);
            List<Project> projects = DescriptionUtil.getReadabilityIndex(GeneralUtil.getProjectsFromJson(jsonResponse));
            float averageIndex = DescriptionUtil.getAverageReadabilityIndex(projects);
            Canva c = new Canva(query, averageIndex, projects);
            if (Canva.getCanvas().size() == 10) {
                Canva.clearCanvas();
            }
            Canva.setCanvasinList(c);

        List<Canva> canvas = new ArrayList<>();
        canvas.addAll(Canva.getCanvas());
        Collections.reverse(canvas);
        return ok(views.html.Home.home.render(canvas));
    }
    /**
     *
     * This method is invoked when the User clicks on any of the listed skills of the projects listed on the home page
     * It redirects to a page which contains the latest active projects' information, which is displayed in the same format as the homepage.
     *
     * @param jobId The Unique identifier for the Job category, which is used to search for a specific job.
     * @return Displays the latest active projects which contain the selected skill, with the limit capped at 10
     * @throws IOException If the data is not upto the specific requirements of the system
     * @throws ParseException If the system encounters an error during the parsing of the API
     * @throws ExecutionException If the system fails to retrieve the necessary data while executing the requests.
     * @throws InterruptedException If the runtime is halted/hindered by some unforeseen reason.
     */
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
    /**
     * This method is invoked when the User clicks on owner_id field on the home page
     * It redirects to a page which contains information about the user.
     * @author Bhargav Bhutwala 40196468
     * @param id owner_id that is unique for each user, and it will fetch user's data based on its owner_id
     * @return displays the page that contains information about the user as well as their last 10 projects
     * @throws IOException thrown when an I/O error occurs.
     * @throws ParseException Signals that an error has been reached unexpectedly while parsing
     */
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
