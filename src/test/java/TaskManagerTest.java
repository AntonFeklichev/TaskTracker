import entity.Epic;
import entity.SubTask;
import entity.Task;
import entity.TaskStatus;
import exceptions.IntersectionException;
import managers.task.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T manager;
    protected Task task;
    protected Epic epic;
    protected SubTask subTask;

    public abstract T doManagers();

    @BeforeEach
    public void init() {
        manager = doManagers();
        task = new Task("task1", "testTask", 1, TaskStatus.NEW);
        epic = new Epic("epic1", "testEpic", 1, TaskStatus.NEW);
        subTask = new SubTask("subTask1", "testSubTask", 1, TaskStatus.NEW, 1);

    }

    @Test
    public void addTaskTest() {
        manager.addTask(task);
        Task result = manager.getTask(task.getId());

        assertEquals(task, result);

    }

    @Test
    public void addEpicTest() {
        manager.addEpic(epic);
        Epic result = manager.getEpic(epic.getId());

        assertEquals(epic, result);
    }

    @Test
    public void addSubTaskTest() {
        manager.addSubTask(subTask);
        SubTask result = manager.getSubTask(subTask.getId());

        assertEquals(subTask, result);
    }

    @Test
    public void getAllTaskTest() {
        manager.addTask(task);
        List<Task> list = manager.getAllTask();
        assertTrue(list.contains(task));
    }

    @Test
    public void getAllEpicTest() {
        manager.addEpic(epic);
        List<Epic> list = manager.getAllEpic();
        assertTrue(list.contains(epic));
    }

    @Test
    public void getAllSubTaskTest() {
        manager.addSubTask(subTask);
        List<SubTask> list = manager.getAllSubTask();
        assertTrue(list.contains(subTask));
    }

    @Test
    public void clearAllTaskTest() {
        manager.addTask(task);
        manager.clearAllTask();
        List<Task> list = manager.getAllTask();
        assertTrue(list.isEmpty());
    }

    @Test
    public void clearAllEpicTest() {
        manager.addEpic(epic);
        manager.clearAllEpic();
        List<Epic> list = manager.getAllEpic();
        assertTrue(list.isEmpty());
    }

    @Test
    public void clearAllSubTaskTest() {
        manager.addSubTask(subTask);
        manager.clearAllSubTask();
        List<SubTask> list = manager.getAllSubTask();
        assertTrue(list.isEmpty());
    }

    @Test
    public void getTaskTest() {
        manager.addTask(task);
        manager.getTask(task.getId());
        List<Task> list = manager.getHistory();
        assertTrue(list.contains(task));
    }

    @Test
    public void getEpicTest() {
        manager.addEpic(epic);
        manager.getEpic(epic.getId());
        List<Task> list = manager.getHistory();
        assertTrue(list.contains(epic));
    }

    @Test
    public void getSubTaskTest() {
        manager.addSubTask(subTask);
        manager.getSubTask(subTask.getId());
        List<Task> list = manager.getHistory();
        assertTrue(list.contains(subTask));
    }

    @Test
    public void updateTaskTest() {
        manager.addTask(task);
        assertTrue(manager.updateTask(task));
    }

    @Test
    public void updateEpicTest() {
        manager.addEpic(epic);
        assertTrue(manager.updateEpic(epic));
    }

    @Test
    public void updateSubTaskTest() {
        manager.addSubTask(subTask);
        assertTrue(manager.updateSubTask(subTask));
    }

    @Test
    public void removeTaskTest() {
        manager.addTask(task);
        manager.removeTask(task.getId());
        List<Task> list = manager.getAllTask();
        assertFalse(list.contains(task));
    }

    @Test
    public void removeEpicTest() {
        manager.addEpic(epic);
        manager.removeEpic(epic.getId());
        List<Epic> list = manager.getAllEpic();
        assertFalse(list.contains(epic));
    }

    @Test
    public void removeSubTaskTest() {
        manager.addSubTask(subTask);
        manager.removeSubTask(subTask.getId());
        List<SubTask> list = manager.getAllSubTask();
        assertFalse(list.contains(subTask));
    }

    @Test
    public void getAllSubTaskByEpicTest() {
        manager.addEpic(epic);
        manager.addSubTask(subTask);
        List<SubTask> list = manager.getAllSubTaskByEpic(epic.getId());
        assertTrue(list.contains(subTask));
    }

    @Test
    public void getNewEpicStatusTest() {

        manager.addEpic(epic);


        List<SubTask> emptyListByEpic = manager.getAllSubTaskByEpic(epic.getId());

        assertTrue(emptyListByEpic.isEmpty());
        assertEquals(TaskStatus.NEW, epic.getStatus());

        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("testSubTask2", "doSubTaskTest2", 2, TaskStatus.DONE, 1);

        manager.addSubTask(subTask2);

        manager.getNewEpicStatus(epic);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void getHistory() {
        List<Task> list = manager.getHistory();
        assertTrue(list.isEmpty());
    }

    @Test
    public void epicStartAndEndTimeTest() {
        manager.addEpic(epic);

        SubTask subTask2 = new SubTask("subTask2",
                "test",
                2,
                TaskStatus.NEW,
                10,
                LocalDateTime.now().plusDays(300),
                epic.getId()
        );

        manager.addSubTask(subTask2);

        SubTask subTask3 = new SubTask("subTask3",
                "test",
                3,
                TaskStatus.NEW,
                160,
                LocalDateTime.now().plusDays(250),
                epic.getId()
        );
        //assertEquals(subTask2.getStartTime() , epic.getStartTime());
        //assertEquals(subTask2.getDuration(), epic.getDuration());
        //assertEquals(subTask2.getEndTime(), epic.getEndTime());


        manager.addSubTask(subTask3);


        //assertEquals(subTask2.getStartTime() , epic.getStartTime());
        //assertEquals(subTask2.getDuration() + subTask3.getDuration() , epic.getDuration());
        //assertEquals(subTask3.getEndTime(), epic.getEndTime());

        SubTask subTask4 = new SubTask("subTask4",
                "test",
                4,
                TaskStatus.NEW,
                160,
                LocalDateTime.now().plusDays(250),
                epic.getId()
        );


        assertThrows(IntersectionException.class, () -> manager.addSubTask(subTask4));

        assertEquals(List.of(epic,subTask3,subTask2), manager.getPrioritizedTasks());

    }

}

