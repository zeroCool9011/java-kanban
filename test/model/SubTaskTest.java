package model;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    TaskManager taskManager = Managers.getDefault();
    final HistoryManager historyManager = Managers.getDefaultHistory();


    @Test
    public void testAddNewSubTask(){
        Epic epic = new Epic(1,"Test addNewEpic", "Test addNewEpic description");
        final int epicId1 = taskManager.addNewEpic(epic).getId();
        SubTask subTask = new SubTask("Test addNewEpic", "Test addNewEpic description",TaskStatus.NEW,epicId1);
        final int taskId = taskManager.addNewSubTask(subTask).getId();

        final Task savedTask = taskManager.getSubTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(subTask, savedTask, "Задачи не совпадают.");

        final ArrayList<SubTask> tasks = taskManager.getSubTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(subTask, tasks.get(0), "Задачи не совпадают.");

    }
    @Test
    public void testEqualityById(){
        SubTask sub1 = new SubTask(1,"Тест1", "Тест Теста1",TaskStatus.NEW,1);
        SubTask sub2 = new SubTask(1,"Тест2", "Тест Теста2",TaskStatus.IN_PROGRESS,2);
        assertEquals(sub1,sub2,"Подзадача не может прикрепиться сама к себе");

    }

    @Test
    public void testNotSelfAttaching(){
        SubTask sub = new SubTask("SubTask1","Тест SubTask1",TaskStatus.NEW,1);
        assertNotEquals(sub.getId(), sub.getEpicId(),"Не совпадает с идентификатором эпика ");
    }


}