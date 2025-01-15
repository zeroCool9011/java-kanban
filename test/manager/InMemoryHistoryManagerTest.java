package manager;

import model.Task;
import model.TaskStatus;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    TaskManager taskManager = Managers.getDefault();
    final HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    public void testHistoryManagerPreservesPreviousVersion() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        taskManager.addNewTask(task);
        taskManager.getTask(task.getId());
        taskManager.getTask(task.getId());
        taskManager.updateTask(task);
        assertEquals(2, taskManager.getHistory().size());

    }
}