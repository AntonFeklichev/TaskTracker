package managers.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import managers.task.TaskManager;

import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler extends AbstractHandler {
    public HistoryHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":

                response = gson.toJson(manager.getHistory());
                rCode = 200;
                break;
            default:
                response = "invalid method";
                rCode = 405;
        }

        exchange.sendResponseHeaders(rCode, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }


    }
}
