package managers.task;

import entity.Epic;
import entity.SubTask;
import entity.Task;
import entity.TaskStatus;
import exceptions.IntersectionException;
import managers.history.InMemoryHistoryManager;
import managers.util.Managers;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {

    private int nextId = 1;


    InMemoryHistoryManager historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();

    Map<Integer, Task> taskMap = new HashMap<>();
    Map<Integer, Epic> epicMap = new HashMap<>();
    Map<Integer, SubTask> subTaskMap = new HashMap<>();

    @Override
    public void addTask(Task task) {
        getIntersectionCheck(task, getPrioritizedTasks());
        task.setId(nextId++);
        taskMap.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        getIntersectionCheck(epic, getPrioritizedTasks());
        epic.setId(nextId++);
        epicMap.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        getIntersectionCheck(subTask, getPrioritizedTasks());
        subTask.setId(nextId++);
        subTaskMap.put(subTask.getId(), subTask);
        Epic epic = epicMap.get(subTask.getEpicId());
        setEpicStartTime(epic);
        setEpicEndTime(epic);
        setEpicDurationStream(epic);

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
            try {
                getIntersectionCheck(task, getPrioritizedTasks());
            } catch (IntersectionException e) {
                return false;
            }
            taskMap.put(task.getId(), task);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean updateEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            try {
                getIntersectionCheck(epic, getPrioritizedTasks());
            } catch (IntersectionException e) {
                return false;
            }
            epicMap.put(epic.getId(), epic);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateSubTask(SubTask subTask) {
        if (subTaskMap.containsKey(subTask.getId())) {
            try {
                getIntersectionCheck(subTask, getPrioritizedTasks());
            } catch (IntersectionException e) {
                return false;
            }
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
            if (subTask.getEpicId() == epic.getId()) {
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
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }

    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    /*private void serEpicDurationUseFor(Epic epic) {
        List<SubTask> subTaskList = getAllSubTaskByEpic(epic);

        int duration = 0;
        for (SubTask subTask : subTaskList) {
            duration += subTask.getDuration();
        }
        epic.setDuration(duration);

    } */

    private void setEpicDurationStream(Epic epic) {
        List<SubTask> subTaskList = getAllSubTaskByEpic(epic);
        int sum = subTaskList.stream()
                .mapToInt(Task::getDuration)
                .sum();
        epic.setDuration(sum);
    }

    private void setEpicStartTime(Epic epic) {
        LocalDateTime startTime = getAllSubTaskByEpic(epic).stream()
                .sorted(Comparator.comparing(Task::getStartTime))
                .findFirst()
                .get()
                .getStartTime();
        epic.setStartTime(startTime);
    }

    public void setEpicEndTime(Epic epic) {
        LocalDateTime endTime = getAllSubTaskByEpic(epic).stream()
                .sorted(Comparator.comparing(Task::getEndTime).reversed())
                .findFirst()
                .get()
                .getEndTime();
        epic.setEndTime(endTime);
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        Set<Task> prioritizedSet = new TreeSet<>(Comparator.comparing(Task::getStartTime).thenComparing(Task :: getId));
        List<Task> prioritizedList = new ArrayList<>();
        prioritizedSet.addAll(taskMap.values().stream()
                .filter(task -> task.getStartTime() != null)
                .collect(Collectors.toList()));
        prioritizedSet.addAll(epicMap.values().stream()
                .filter(epic -> epic.getStartTime() != null)
                .collect(Collectors.toList()));
        prioritizedSet.addAll(subTaskMap.values().stream()
                .filter(subTask -> subTask.getStartTime() != null)
                .collect(Collectors.toList()));

        prioritizedList.addAll(prioritizedSet);
        prioritizedList.addAll(taskMap.values().stream()
                .filter(task -> task.getStartTime() == null)
                .collect(Collectors.toList()));
        prioritizedList.addAll(epicMap.values().stream()
                .filter(epic -> epic.getStartTime() == null)
                .collect(Collectors.toList()));
        prioritizedList.addAll(subTaskMap.values().stream()
                .filter(subTask -> subTask.getStartTime() == null)
                .collect(Collectors.toList()));
        return prioritizedList;


    }

    public List<Task> getPrioritizedTasksCustom() {

        Comparator<Task> startTimeTaskComparator = (task1, task2) -> {
            if (task1.getStartTime() == null) return 1;
            if (task2.getStartTime() == null) return -1;
            if (task1.getStartTime().isBefore(task2.getStartTime())) return -1;
            if (task1.getStartTime().isAfter(task2.getStartTime())) return 1;
            else return 0;

        };
        List<Task> prioritizedList = new ArrayList<>();
        Set<Task> prioritizedSet = new TreeSet<>(startTimeTaskComparator);
        prioritizedSet.addAll(taskMap.values());
        prioritizedSet.addAll(epicMap.values());
        prioritizedSet.addAll(subTaskMap.values());

        prioritizedList.addAll(prioritizedSet);
        return prioritizedList;
    }

    public void getIntersectionCheck(Task task, List<Task> prioritizedList) {

        if (task.getStartTime() == null || task.getEndTime() == null) {
            return;
        }
        prioritizedList.forEach(taskFromList -> {
            if (taskFromList.getStartTime() == null || taskFromList.getEndTime() == null) {
                return;
            }
            if (!(task.getEndTime().isBefore(taskFromList.getStartTime())
                    || task.getStartTime().isAfter(taskFromList.getEndTime())))
                throw new IntersectionException(task + "пересекается с " + taskFromList);
        });
    }

}
