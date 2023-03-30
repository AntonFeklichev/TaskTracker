package managers.task;

import com.google.gson.Gson;
import entity.Epic;
import entity.SubTask;
import entity.Task;
import exceptions.ManagerSaveException;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {


    private final String fileName;

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }


    private void save() {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            writer.write(json);


        } catch (IOException exception) {
            throw new ManagerSaveException("Файл не сохранен");

        }
    }

    public static FileBackedTasksManager loadFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, FileBackedTasksManager.class);


        } catch (IOException exception) {
            throw new RuntimeException("Не удалось прочитать из файла");
        }
    }


    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void clearAllTask() {
        super.clearAllTask();
        save();
    }

    @Override
    public void clearAllEpic() {
        super.clearAllEpic();
        save();
    }

    @Override
    public void clearAllSubTask() {
        super.clearAllSubTask();
        save();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = super.getSubTask(id);
        save();
        return subTask;
    }

    @Override
    public Boolean updateTask(Task task) {
        Boolean updateTask = super.updateTask(task);
        save();
        return updateTask;
    }

    @Override
    public Boolean updateEpic(Epic epic) {
        Boolean updateEpic = super.updateEpic(epic);
        save();
        return updateEpic;
    }

    @Override
    public Boolean updateSubTask(SubTask subTask) {
        Boolean updateSubTask = super.updateSubTask(subTask);
        save();
        return updateSubTask;
    }

    @Override
    public void removeTask(Task task) {
        super.removeTask(task);
        save();
    }

    @Override
    public void removeEpic(Epic epic) {
        super.removeEpic(epic);
        save();
    }

    @Override
    public void removeSubTask(SubTask subTask) {
        super.removeSubTask(subTask);
        save();
    }
}


