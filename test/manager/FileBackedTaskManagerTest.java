package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.Test;
import manager.FileBackedTaskManager;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileBackedTaskManagerTest {
    File file = new File("./resources/task.csv");
    TaskManager taskManager = Managers.getDefault();
    FileBackedTaskManager taskManagerInFile = FileBackedTaskManager.loadFromFile(file);
    @Test
    public void correctlyLoadFromFile() {
        Task task1 = new Task(1,"model.Task #1", "Task1 description", TaskStatus.NEW);
        Epic epic1 = new Epic(2,"model.Epic#1", "Epic1 description");
       // final int epicId1 = taskManager.addNewEpic(epic1).getId();
       // SubTask subTask1 = new SubTask(3,"model.SubTask#3", "SubTask3 description", TaskStatus.DONE, epicId1);
        taskManager.addNewTask(task1);
        taskManager.addNewEpic(epic1);
        assertNotNull(taskManager.getTask(task1.getId()), "Задача не найдена по ID.");
        assertNotNull(taskManager.getEpic(epic1.getId()), "Эпик не найден по ID.");

    }



}