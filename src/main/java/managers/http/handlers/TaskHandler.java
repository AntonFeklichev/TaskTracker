package managers.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import entity.Task;
import managers.task.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class TaskHandler extends AbstractHandler {

    public TaskHandler(TaskManager manager) {
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

                    response = gson.toJson(manager.getTask(id));
                } else {
                    response = gson.toJson(manager.getAllTask());
                }
                rCode = 200;
                break;
            case "POST":

                try (InputStream is = exchange.getRequestBody()) {
                    Task task = gson.fromJson(new String(is.readAllBytes()), Task.class);
                    if (manager.getTask(task.getId()) == null) {
                        manager.addTask(task);
                    } else {
                        manager.updateTask(task);
                    }
                }
                rCode = 201;


                break;
            case "DELETE":
                if (queryMap.containsKey("id")) {
                    int id = Integer.parseInt(queryMap.get("id"));
                    manager.removeTask(id);
                    rCode = 204;
                    break;
                }
                manager.clearAllTask();

                rCode = 200;
                break;


        }

        exchange.sendResponseHeaders(rCode, responseLength);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
