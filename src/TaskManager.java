import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


public class TaskManager {
    HashMap<Integer, Task> tasks;
    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;
    private int id;


    public TaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        this.id = 0;


    }

    public int generatorId() {
        return ++id;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>(tasks.values());
        return taskArrayList;
    }

    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicArrayList = new ArrayList<>(epics.values());
        return epicArrayList;
    }

    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>(subTasks.values());
        return subTaskArrayList;
    }

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

    public Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    public SubTask getSubTask(int subtaskId) {
        return subTasks.get(subtaskId);
    }

    public Epic getEpic(int epicId) {
        return epics.get(epicId);
    }

    public Task addNewTask(Task task) {
        if (task != null && !tasks.containsKey(task.getId())) {
            int newId = task.getId();
            newId = generatorId();
            task.setId(newId);
            tasks.put(newId, task);
        } else {
            return null;
        }
        return task;

    }

    public Epic addNewEpic(Epic epic) {
        if (epic != null && !epics.containsKey(epic.getId())) {
            int newId = epic.getId();
            newId = generatorId();
            epic.setId(newId);
            epics.put(newId, epic);
        } else {
            return null;
        }
        return epic;
    }

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

    public void printAllSubTasks() {
        List<SubTask> allSubTasks = getSubTasks();
        for (SubTask subTask : allSubTasks) {
            System.out.println(subTask);
        }
    }


    public void updateEpic(Epic epic) {
        ArrayList<SubTask> subTaskArrayList = getEpicSubTask(epic.getId());

        if (epic.getSubTasks().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        boolean isNew = false;
        boolean isDone = true;

        for (SubTask subTask : subTaskArrayList) {
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

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subTask) {
        Epic epic = getEpic(subTask.getId());
        subTasks.put(subTask.getId(), subTask);
        updateEpic(epic);

    }


    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteSubTaskId(int id) {
        SubTask subTask = subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getSubTasks().remove(id);
        updateEpic(epic);

    }


    public void deliteTaskId(int id) {
        tasks.remove(id);

    }

    public void deleteEpicId(int id) {
        Epic epic = epics.get(id);
        for (Integer subtaskId : epic.getSubTasks()) {
            subTasks.remove(subtaskId);
        }
        epics.remove(id);

    }

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void deleteSubTasks() {
        for (Integer subTaskId : new ArrayList<>(subTasks.keySet())) {
            subTasks.remove(subTaskId);
        }

    }


}


