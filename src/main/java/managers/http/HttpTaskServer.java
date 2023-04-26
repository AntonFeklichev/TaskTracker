package managers.http;

import com.sun.net.httpserver.HttpServer;
import managers.http.handlers.*;
import managers.task.TaskManager;
import managers.util.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final String ADRESS = "localhost";
    private static final int PORT = 8080;


    private final HttpServer httpServer;



    public HttpTaskServer() throws IOException {

        TaskManager manager = Managers.getDefault();
        httpServer = HttpServer.create(new InetSocketAddress(ADRESS, PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler(manager));
        httpServer.createContext("/tasks/history", new HistoryHandler(manager));
        httpServer.createContext("/tasks/epic", new EpicHandler(manager));
        httpServer.createContext("/tasks/subtask", new SubTaskHandler(manager));
        httpServer.createContext("/tasks/subtask/epic", new SubTaskByEpicHandler(manager));
        httpServer.start();

    }


}
