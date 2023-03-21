package managers.task;

import entity.Epic;
import entity.SubTask;
import entity.Task;

import java.util.List;

public interface TaskManager {


    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);


    List<Task> getAllTask();

    List<Epic> getAllEpic();

    List<SubTask> getAllSubTask();

    void clearAllTask();

    void clearAllEpic();

    void clearAllSubTask();

    Task getTask(int id);

    Epic getEpic(int id);

    SubTask getSubTask(int id);


    Boolean updateTask(Task task);

    Boolean updateEpic(Epic epic);

    Boolean updateSubTask(SubTask subTask);

    void removeTask(Task task);

    void removeEpic(Epic epic);

    void removeSubTask(SubTask subTask);

    List<SubTask> getAllSubTaskByEpic(Epic epic);

    void getNewEpicStatus(Epic epic);

    List<Task> getHistory();


}
