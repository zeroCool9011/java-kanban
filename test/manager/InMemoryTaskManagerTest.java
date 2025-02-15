package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.Test;


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

        taskManager.addNewTask(task);
        taskManager.addNewEpic(epic);
        //final int epicId1 = taskManager.addNewEpic(epic).getId();
       // SubTask subTask = new SubTask("Subtask", "Desc",  TaskStatus.NEW,2);
        //taskManager.addNewSubTask(subTask).getEpicId();

        assertNotNull(taskManager.getTask(task.getId()), "Задача не найдена по ID.");
        assertNotNull(taskManager.getEpic(epic.getId()), "Эпик не найден по ID.");
      // assertNotNull(taskManager.getSubTask(3), "Подзадача не найдена по ID.");
    }



}