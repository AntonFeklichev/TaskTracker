package managers.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import entity.Epic;
import managers.task.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class EpicHandler extends AbstractHandler {


    public EpicHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();
        String queryString = exchange.getRequestURI().getQuery();

        Map<String, String> queryMap = doQueryMap(queryString);

        switch (method) {
            case "GET":
                if (queryMap.containsKey("id")) {
                    int id = Integer.parseInt(queryMap.get("id"));
                    response = gson.toJson(manager.getEpic(id));
                } else {
                    response = gson.toJson(manager.getAllEpic());
                }
                rCode = 200;
                break;
            case "POST":
                try (InputStream is = exchange.getRequestBody()) {
                    Epic epic = gson.fromJson(new String(is.readAllBytes()), Epic.class);
                    if (manager.getEpic(epic.getId()) == null) {
                        manager.addEpic(epic);
                    } else {
                        manager.updateEpic(epic);
                    }
                }
                rCode = 201;
                break;
            case "DELETE":
                if (queryMap.containsKey("id")) {
                    int id = Integer.parseInt(queryMap.get("id"));
                    manager.removeEpic(id);
                    rCode = 204;
                    break;
                }
                manager.clearAllEpic();
                break;

        }
        exchange.sendResponseHeaders(rCode, responseLength);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
