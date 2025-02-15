package manager;

import model.TaskStatus;
import model.Task;
import model.Epic;
import model.SubTask;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    public HashMap<Integer, Task> tasks;
    public final HashMap<Integer, SubTask> subTasks;
    public final HashMap<Integer, Epic> epics;
    private int id;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        this.id = 0;

    }

    @Override
    public int generatorId() {
        return ++id;
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>(tasks.values());
        return taskArrayList;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicArrayList = new ArrayList<>(epics.values());
        return epicArrayList;
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>(subTasks.values());
        return subTaskArrayList;
    }

    @Override
    public ArrayList<SubTask> getEpicSubTask(int id) throws NoSuchElementException {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<SubTask> result = new ArrayList<>();
            for (int i = 0; i < epic.getSubTasks().size(); i++) {
                result.add(subTasks.get(epic.getSubTasks().get(i)));

            }
            return result;
        } else {
            throw new NoSuchElementException("Эпик с данным id отсутствует!!!");

        }

    }

    @Override
    public Task getTask(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.addHistory(task);
        return task;
    }

    @Override
    public SubTask getSubTask(int subtaskId) {
        SubTask  subTask = subTasks.get(subtaskId);
        if (subTask != null) {
            historyManager.addHistory(subTask);

        } else {
            return null;
        }
        return subTask;

    }

    @Override
    public Epic getEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.addHistory(epic);
        } else {
            return null;
        }
        return epic;
    }

    @Override
    public Task addNewTask(Task task) {
        if (task != null) {
            int newId = task.getId();
            newId = generatorId();
            task.setId(newId);
            tasks.put(newId, task);

        } else {
            return null;
        }
        return task;

    }

    @Override
    public Epic addNewEpic(Epic epic) {
        if (epic != null) {
            int newId = epic.getId();
            newId = generatorId();
            epic.setId(newId);
            epics.put(newId, epic);


        } else {
            return null;
        }
        return epic;
    }

    @Override
    public SubTask addNewSubTask(SubTask subTask) {

        int subId = subTask.getId();
        if (subId == 0) {
            subId = generatorId();
            subTask.setId(subId);

        }

        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            throw new RuntimeException("Такого эпика нет!!");
        } else {
            subTasks.put(subId, subTask);

            epic.addSubTaskId(subId);
            updateEpic(epic);
        }

        return subTask;

    }


    @Override
    public void updateEpic(Epic epic) {
        ArrayList<SubTask> subTaskArrayList = getEpicSubTask(epic.getId());

        if (epic.getSubTasks().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        boolean isNew = false;
        boolean isDone = true;

        for (SubTask subTask : subTaskArrayList) {
            //TaskStatus status = subTasks.get(epics).getStatus();
            TaskStatus status = subTask.getStatus();
            if (status == TaskStatus.NEW) {
                isNew = true;

            } else if (status != TaskStatus.DONE) {
                isDone = false;

            }

            if (isNew) {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            } else if (isDone) {
                epic.setStatus(TaskStatus.DONE);
            } else {
                epic.setStatus(TaskStatus.NEW);
            }
        }

    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        Epic epic = getEpic(subTask.getId());
        subTasks.put(subTask.getId(), subTask);
        updateEpic(epic);

    }

    @Override
    public void deleteTaskId(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);

    }

    @Override
    public void deleteSubTaskId(int id) {
        SubTask subTask = subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getSubTasks().remove(id);
        updateEpic(epic);
        historyManager.remove(id);

    }

    @Override
    public void deleteEpicId(int id) {
        Epic epic = epics.get(id);
        for (Integer subtaskId : epic.getSubTasks()) {
            subTasks.remove(subtaskId);
        }
        epics.remove(id);
        historyManager.remove(id);

    }

    @Override
    public void deleteTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        epics.clear();
        deleteSubTasks();

    }

    @Override
    public void deleteSubTasks() {
        ArrayList<Integer> subTaskIdRemove = new ArrayList<>(subTasks.keySet());

        for (Integer subTaskId : subTaskIdRemove) {
            SubTask subTask = subTasks.get(subTaskId);
            Epic epic = epics.get(subTask.getEpicId());
            epic.removeSubTask(subTaskId);
            updateEpic(epic);
            subTasks.remove(subTaskId);
            historyManager.remove(subTaskId);
        }

    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

}


