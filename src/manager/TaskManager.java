package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface TaskManager {
    int generatorId();

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubTasks();

    ArrayList<SubTask> getEpicSubTask(int id) throws NoSuchElementException;

    Task getTask(int taskId);

    SubTask getSubTask(int subtaskId);

    Epic getEpic(int epicId);

    Task addNewTask(Task task);

    Epic addNewEpic(Epic epic);

    SubTask addNewSubTask(SubTask subTask);

    void updateEpic(Epic epic);

    void updateTask(Task task);

    void updateSubTask(SubTask subTask);

    void deleteTaskId(int taskId);

    void deleteSubTaskId(int id);

    void deleteEpicId(int id);

    void deleteTasks();

    void deleteEpics();

    void deleteSubTasks();

    ArrayList<Task> getHistory();



}
