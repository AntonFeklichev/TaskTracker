package managers.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import entity.SubTask;
import managers.task.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class SubTaskHandler extends AbstractHandler {

    public SubTaskHandler(TaskManager manager) {
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
                    response = gson.toJson(manager.getSubTask(id));
                } else {
                    response = gson.toJson(manager.getAllSubTask());
                }

                rCode = 200;
                break;

            case "POST":
                try (InputStream is = exchange.getRequestBody()) {
                    SubTask subTask = gson.fromJson(new String(is.readAllBytes()), SubTask.class);
                    if (manager.getSubTask(subTask.getId()) == null) {
                        manager.addSubTask(subTask);
                    } else {
                        manager.updateSubTask(subTask);
                    }
                    rCode = 201;
                }
                break;

            case "DELETE":
                if (queryMap.containsKey("id")) {
                    int id = Integer.parseInt(queryMap.get("id"));
                    manager.removeSubTask(id);
                } else {
                    manager.clearAllSubTask();
                }
                rCode = 204;
                break;

        }

        exchange.sendResponseHeaders(rCode, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }

    }
}
