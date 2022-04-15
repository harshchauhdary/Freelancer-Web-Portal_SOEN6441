package Util;

import model.Canva;
import play.mvc.Http;

import java.util.HashMap;
import java.util.List;

public class Session {

    private static HashMap<String, List<Canva>> browsers;

    static {
         browsers = new HashMap<>();
    }

    public static  boolean checkSessionExist(Http.Request request) {
        return request.session().get("session").orElse(null) != null;
    }

    public static String getSessionValue(Http.Request request) {
        return request.session().get("session").orElse(null);
    }

    public static List<Canva> getDataFromSession(String session){
        return browsers.get(session);
    }
    public static HashMap<String,List<Canva>> getSession(){
        return browsers;
    }
    public static void setSession(HashMap<String, List<Canva>> browsers){
        browsers = browsers;
    }

    public static void setDataForSession(String id,List<Canva> c){
        browsers.replace(id,c);
    }
}
