package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    TaskManager taskManager = Managers.getDefault();
    final HistoryManager historyManager = Managers.getDefaultHistory();


    @Test
    public void testGetInitializedTaskManager() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Менеджер задач не должен быть null.");
    }

    @Test
    public void testTaskManagerAddsAndFindsDifferentTaskTypes() {
        Task task = new Task("Task", "Desc", TaskStatus.NEW);
        Epic epic = new Epic("Epic", "Desc");
        SubTask subTask = new SubTask("Subtask", "Desc",  TaskStatus.NEW,epic.getId());

        taskManager.addNewTask(task);
        taskManager.addNewTask(epic);
        taskManager.addNewTask(subTask);

        assertNotNull(taskManager.getTask(task.getId()), "Задача не найдена по ID.");
        assertNotNull(taskManager.getTask(epic.getId()), "Эпик не найден по ID.");
        assertNotNull(taskManager.getTask(subTask.getId()), "Подзадача не найдена по ID.");
    }


}