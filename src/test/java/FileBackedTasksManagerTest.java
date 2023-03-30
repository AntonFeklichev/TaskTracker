import managers.task.FileBackedTasksManager;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static managers.task.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private static final String FILE_NAME = "doEpicTest.json";

    @Override
    public FileBackedTasksManager doManagers() {
        return new FileBackedTasksManager(FILE_NAME);
    }

    @Test
    protected void saveTest() {

        manager.addEpic(epic);

        assertTrue(Files.exists(Path.of(FILE_NAME)));

        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(FILE_NAME);

        assertTrue(manager.getHistory().isEmpty());
        assertEquals(manager.getEpic(1), fileBackedTasksManager2.getEpic(1));
        assertTrue(manager.getAllSubTaskByEpic(epic).isEmpty());


    }


}
