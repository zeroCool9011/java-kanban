package model;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    TaskManager taskManager = Managers.getDefault();
    final HistoryManager historyManager = Managers.getDefaultHistory();


    @Test
    public void testAddNewEpic(){
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        final int taskId = taskManager.addNewEpic(epic).getId();

        final Task savedTask = taskManager.getEpic(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(epic, savedTask, "Задачи не совпадают.");

        final ArrayList<Epic> tasks = taskManager.getEpics();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(epic, tasks.get(0), "Задачи не совпадают.");

    }
    @Test
    public void testSubtaskCannotBeItsOwnEpic() {

        Epic epic = new Epic("Эпик 1", "Описание эпика");

        Integer epicId = taskManager.addNewEpic(epic).getId();
        SubTask subTask1 = new SubTask("","",TaskStatus.NEW,epicId);
        Integer subTask1Id = taskManager.addNewSubTask(subTask1).getId();
        SubTask subTask2 = new SubTask("","",TaskStatus.NEW,subTask1Id);
        assertEquals(1,taskManager.getEpics().size());

    }

    @Test
    public void equalsEpicWithOneId(){
        Epic epic1 = new Epic("Epic1","Task Epic1");
        taskManager.addNewEpic(epic1);
        Epic epic2 = new Epic("Epic2","Task Epic2");
        epic2.setId(epic1.getId());
        assertEquals(epic1,epic2);

    }

    @Test
    public void equalsEpicWithTask(){
        Epic epic1 = new Epic("Epic1","Task Epic1");
        taskManager.addNewEpic(epic1);
        Task task1 = new Task("Task1","Tasks Task1",TaskStatus.NEW);
        task1.setId(epic1.getId());
        assertNotEquals(epic1,task1);
    }

    @Test
    public void equalsEpicWithSubTask(){
        Epic epic1 = new Epic("Epic1","Task Epic1");
        taskManager.addNewEpic(epic1);
        SubTask subTask1 = new SubTask("SubTask1","Tasks SubTask1",TaskStatus.NEW, epic1.getId());
        subTask1.setId(epic1.getId());
        assertNotEquals(epic1,subTask1);
    }

}