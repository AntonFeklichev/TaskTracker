package managers.http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpHandler;
import managers.task.TaskManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHandler implements HttpHandler {

    protected String response = "";
    protected Gson gson;
    protected TaskManager manager;
    protected int rCode = 200;
    protected int responseLength = 0;


    public AbstractHandler(TaskManager manager) {
        this.manager = manager;
        this.gson = doGson();
    }

    private Gson doGson() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson;
    }


    protected Map<String, String> doQueryMap(String queryString) {
        if (queryString == null || queryString.isBlank()) {
            return Collections.emptyMap();
        } else {
            Map<String, String> queryMap = new HashMap<>();
            String[] splitByAmpersand = queryString.split("&");
            for (String string : splitByAmpersand) {
                String[] stingPairsToMap = string.split("=");
                queryMap.put(stingPairsToMap[0], stingPairsToMap[1]);

            }
            return queryMap;

        }


    }


}
