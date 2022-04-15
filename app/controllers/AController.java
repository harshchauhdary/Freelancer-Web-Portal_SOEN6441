package controllers;

import Util.GeneralUtil;
import Util.Session;
import model.Canva;
import play.cache.SyncCacheApi;
import play.mvc.Controller;

//import Helper.SessionHelper;
//import actors.SupervisorActor;
import play.cache.AsyncCacheApi;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.libs.streams.ActorFlow;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.WebSocket;
import play.libs.ws.WSClient;
//import services.GithubService;

import java.util.*;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.ok;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import  actors.*;

import javax.inject.Inject;

public class AController extends Controller {
    private final FormFactory formFactory;
    private SyncCacheApi cache;
    private final AssetsFinder assetsFinder;
    private HttpExecutionContext httpExecutionContext;
    @Inject
    private Materializer materializer;
    @Inject
    private ActorSystem actorSystem;

    private WSClient ws;

    @Inject
    public AController(HttpExecutionContext httpExecutionContext, AssetsFinder assetsFinder, FormFactory formFactory, SyncCacheApi cache, Materializer materializer,WSClient ws, ActorSystem actorSystem) {
        this.formFactory = formFactory;
        this.cache = cache;
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.assetsFinder = assetsFinder;
        this.browsers = new HashMap<>();
        this.ws = ws;
        this.httpExecutionContext = httpExecutionContext;
    }

    /**
     * User and their list of canvas
     */
    private HashMap<String, List<Canva>> browsers;
    private String uId;

    public Result index(Http.Request request) {
        HashMap<String,List<Canva>> browsers = Session.getSession();
        Optional<String> user = request.session().get("user");
        if (!user.isPresent()) {
            String id = GeneralUtil.generateId(browsers.keySet());
            browsers.put(id, new ArrayList<>());
            Session.setSession(browsers);
            uId = id;
            return ok(views.html.sample.render(request,new ArrayList<>())).addingToSession(request, "user", id);
        } else {
            if (browsers.get(user.get()) == null) {
                browsers.put(user.get(), new ArrayList<>());
                Session.setSession(browsers);
            }
            uId = user.get();
            return ok(views.html.sample.render(request,Session.getDataFromSession(uId)));
        }
    }


    public WebSocket ws() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(out -> SupervisorActor.props(out,ws, cache,uId), actorSystem, materializer));
    }
}
