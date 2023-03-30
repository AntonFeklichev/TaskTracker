import entity.Task;
import entity.TaskStatus;
import managers.history.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager inMemoryHistoryManager;

    @BeforeEach
    protected void doManagers() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }


    @Test
    protected void getHistoryTest() {

        List<Task> history = inMemoryHistoryManager.getHistory();

        assertTrue(history.isEmpty());

    }

    @Test
    protected void addTest() {
        Task task1 = new Task("testTask1", "test 1", 1, TaskStatus.NEW);
        Task task2 = new Task("testTask2", "test 1", 2, TaskStatus.NEW);
        Task task3 = new Task("testTask3", "test 1", 2, TaskStatus.NEW);

        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);

        List<Task> history = inMemoryHistoryManager.getHistory();

        assertFalse(history.contains(task2));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    protected void removeTest(int testId) {

        Task task1 = new Task("testTask1", "test 1", 1, TaskStatus.NEW);
        Task task2 = new Task("testTask2", "test 1", 2, TaskStatus.NEW);
        Task task3 = new Task("testTask3", "test 1", 3, TaskStatus.NEW);

        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);


        inMemoryHistoryManager.remove(testId);

        List<Task> history = inMemoryHistoryManager.getHistory();

        assertTrue(history.stream()
                .noneMatch(task -> task.getId() == testId));
        //assertTrue(history.get(0).equals(task2));


    }
}
