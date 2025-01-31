package manager;

import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    TaskManager taskManager = Managers.getDefault();
    final HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    public void testHistoryManagerPreservesPreviousVersion() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        Task task1 = new Task("Task1", "Description1", TaskStatus.NEW);
        taskManager.addNewTask(task);
        taskManager.addNewTask(task1);
        taskManager.getTask(task.getId());
        taskManager.getTask(task1.getId());
        taskManager.updateTask(task);
        taskManager.updateTask(task1);
        assertEquals(2, taskManager.getHistory().size());

    }
    @Test
    public  void  testRepitTaskInHistory(){
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        taskManager.addNewTask(task);
        taskManager.getTask(task.getId());
        taskManager.getTask(task.getId());
        taskManager.updateTask(task);
        assertEquals(1, taskManager.getHistory().size());

    }

    @Test
    void testAddHistory() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        historyManager.addHistory(task);
        assertEquals(1, historyManager.getHistory().size());

    }

    @Test
    void TestRemove() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        historyManager.addHistory(task);
        historyManager.remove(0);
        assertEquals(0, historyManager.getHistory().size());
    }

    @Test
    void testGetHistory() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        historyManager.addHistory(task);
        ArrayList<Task> list = historyManager.getHistory();
        assertEquals(task, list.get(0));

    }
}