package managers.task;

import com.google.gson.Gson;
import entity.Epic;
import entity.SubTask;
import entity.Task;

import java.io.*;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    //FileWriter fileWriter = new FileWriter("Task.csv");

    /* public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("Task.csv");
        fileWriter.write("Hello");
        Gson gson = new Gson();

    } */


    private final String fileName;

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }


    private void save() {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            writer.write(json);

            //writer.close();

        } catch (IOException exception) {
            System.out.println("Не удалось записать файл");

        }
    }

    public static FileBackedTasksManager loadFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            //reader.read(fileName);
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
    public void addEpic(Epic epic){
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }



}


