import managers.task.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public InMemoryTaskManager doManagers() {
        return new InMemoryTaskManager();
    }

}
