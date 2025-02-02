package model;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskTest {
    TaskManager taskManager = Managers.getDefault();
    final HistoryManager historyManager = Managers.getDefaultHistory();


    @Test
    public void testAddNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        final int taskId = taskManager.addNewTask(task).getId();

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void testAdd() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        historyManager.addHistory(task);
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "После добавления задачи, история не должна быть пустой.");
        assertEquals(1, history.size(), "После добавления задачи, история не должна быть пустой.");
    }

    @Test
    public void testUniqueTaskId() {
        Task task1 = new Task(1, "Task 1", "Desc", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 1", "Desc", TaskStatus.NEW);

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);

        assertNotNull(taskManager.getTask(1), "Задача 1 не найдена.");
        assertNotNull(taskManager.getTask(2), "Задача 2 не найдена.");
    }
    @Test
    public void equalsTaskWithOneId(){
        Task task1 = new Task("Task1","Task Task1",TaskStatus.NEW);
        taskManager.addNewTask(task1);
        Task task2 = new Task("Task2","Task Task2",TaskStatus.NEW);
        task2.setId(task1.getId());
        assertEquals(task1,task2);

    }
}