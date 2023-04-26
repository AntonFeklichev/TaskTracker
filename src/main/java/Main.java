import entity.Epic;
import entity.SubTask;
import entity.Task;
import entity.TaskStatus;
import managers.history.InMemoryHistoryManager;
import managers.http.HttpTaskServer;
import managers.http.kv.KVServer;
import managers.task.FileBackedTasksManager;
import managers.task.InMemoryTaskManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        new KVServer().start();

//        Epic epic1 = new Epic("Java", "BeJavaDeveloper", 1, TaskStatus.NEW);
//        SubTask subTask1 = new SubTask("Diplom", "getDimlom", 1, TaskStatus.DONE, 1);
//        SubTask subTask2 = new SubTask("Job", "getJob", 2, TaskStatus.DONE, 1);
//
//        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
//        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        /*inMemoryTaskManager.putInEpicMap(epic1);
        inMemoryTaskManager.putInSubTaskMap(subTask1);
        inMemoryTaskManager.putInSubTaskMap(subTask2);
        inMemoryTaskManager.getNewEpicStatus(epic1);
        //inMemoryTaskManager.removeSubTask(subTask1);
        //inMemoryTaskManager.removeEpic(epic1);

        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println(inMemoryTaskManager.getAllSubTask());
        System.out.println(inMemoryTaskManager.getAllSubTaskByEpic(epic1));


        inMemoryHistoryManager.add(epic1);
        inMemoryHistoryManager.add(subTask1);
        inMemoryHistoryManager.add(subTask2);
        inMemoryHistoryManager.remove(12);


        System.out.println(inMemoryHistoryManager.getHistory()); */

//        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("save.json");
//        fileBackedTasksManager.addTask(new Task("lesson", "finish lesson", 1, TaskStatus.NEW));
//        fileBackedTasksManager.addTask(new Task("lesson2", "finish lesson2", 1, TaskStatus.NEW));

        //fileBackedTasksManager = FileBackedTasksManager.loadFromFile("save.json");
        //System.out.println(fileBackedTasksManager.getAllTask());


    }
}
