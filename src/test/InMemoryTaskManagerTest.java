package test;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
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
    public void testAdd() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        historyManager.addHistory(task);
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "После добавления задачи, история не должна быть пустой.");
        assertEquals(1, history.size(), "После добавления задачи, история не должна быть пустой.");
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
    public void testHistoryManagerPreservesPreviousVersion() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        taskManager.addNewTask(task);
        taskManager.getTask(task.getId());
        taskManager.getTask(task.getId());
        taskManager.updateTask(task); 
        assertEquals(2, taskManager.getHistory().size());

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
    public void equalsTaskWithOneId(){
        Task task1 = new Task("Task1","Task Task1",TaskStatus.NEW);
        taskManager.addNewTask(task1);
        Task task2 = new Task("Task2","Task Task2",TaskStatus.NEW);
        task2.setId(task1.getId());
        assertEquals(task1,task2);

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