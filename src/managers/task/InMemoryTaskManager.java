package managers.task;

import entity.Epic;
import entity.SubTask;
import entity.Task;
import entity.TaskStatus;
import managers.history.InMemoryHistoryManager;
import managers.util.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int nextId = 1;


    InMemoryHistoryManager historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();

    Map<Integer, Task> taskMap = new HashMap<>();
    Map<Integer, Epic> epicMap = new HashMap<>();
    Map<Integer, SubTask> subTaskMap = new HashMap<>();

    @Override
    public void addTask(Task task) {
        task.setId(nextId++);
        taskMap.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(nextId++);
        epicMap.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(nextId++);
        subTaskMap.put(subTask.getId(), subTask);
    }

    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epicMap.values());
    }

    @Override
    public List<SubTask> getAllSubTask() {
        return new ArrayList<>(subTaskMap.values());
    }

    @Override
    public void clearAllTask() {
        taskMap.clear();
    }

    @Override
    public void clearAllEpic() {
        epicMap.clear();
    }

    @Override
    public void clearAllSubTask() {
        subTaskMap.clear();
    }

    @Override
    public Task getTask(int id) {
        Task task = taskMap.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epicMap.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subTaskMap.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Boolean updateTask(Task task) {
        if (taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean updateEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateSubTask(SubTask subTask) {
        if (subTaskMap.containsKey(subTask.getId())) {
            subTaskMap.put(subTask.getId(), subTask);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void removeTask(Task task) {
        taskMap.remove(task.getId());
    }

    @Override
    public void removeEpic(Epic epic) {
        epicMap.remove(epic.getId());
    }

    @Override
    public void removeSubTask(SubTask subTask) {
        subTaskMap.remove(subTask.getId());
    }

    @Override
    public List<SubTask> getAllSubTaskByEpic(Epic epic) {

        List<SubTask> subTaskList = new ArrayList<>();
        for (SubTask subTask : subTaskMap.values()) {
            if (subTask.getEpicId() == epic.id) {
                subTaskList.add(subTask);
            }
        }
        return subTaskList;
    }

    @Override
    public void getNewEpicStatus(Epic epic) {
        int newCounter = 0;
        int doneCounter = 0;
        List<SubTask> subTaskList = getAllSubTaskByEpic(epic);
        for (SubTask subTask : subTaskList) {
            if (subTask.getStatus() == TaskStatus.NEW) {
                newCounter++;

            } else if (subTask.getStatus() == TaskStatus.DONE) {
                doneCounter++;


            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
                return;
            }

        }
        if (newCounter == subTaskList.size() || subTaskList.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        }

        if (doneCounter == subTaskList.size()) {
            epic.setStatus(TaskStatus.DONE);
        }

    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


}
