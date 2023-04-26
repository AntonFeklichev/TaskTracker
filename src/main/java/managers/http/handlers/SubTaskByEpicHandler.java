package managers.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import managers.task.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class SubTaskByEpicHandler extends AbstractHandler {

    public SubTaskByEpicHandler(TaskManager manager) {
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

                    response = gson.toJson(manager.getAllSubTaskByEpic(id));
                    rCode = 200;
                    break;
                } else {
                    rCode = 400;
                    break;
                }
            default:
                rCode = 405;
        }

        exchange.sendResponseHeaders(rCode, responseLength);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }

    }
}
