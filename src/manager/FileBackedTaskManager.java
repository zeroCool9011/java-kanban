package manager;

import exceptions.ManagerSaveException;
import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.io.*;
import java.util.ArrayList;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    protected void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : tasks.values()) {
                writer.write(task.toString() + "\n");

            }

            for (Epic epic : epics.values()) {
                writer.write(epic.toString() + "\n");

            }

            for (SubTask subTask : subTasks.values()) {
                writer.write(subTask.toString() + "\n");

            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных в файл");
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {

        FileBackedTaskManager managerRestored = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                Task task = fromString(line);
                if (task.getId() > managerRestored.generatorId()) {
                    managerRestored.generatorId();
                }
                switch (task.getType()) {
                    case TASK:
                        managerRestored.tasks.put(task.getId(), task);
                        break;
                    case SUBTASK:
                        managerRestored.subTasks.put(task.getId(), (SubTask) task);
                        break;
                    case EPIC:
                        managerRestored.epics.put(task.getId(), (Epic) task);
                        break;
                }
            }
            for (SubTask subTask : managerRestored.subTasks.values()) {
                Epic epic = managerRestored.epics.get(subTask.getEpicId());
                epic.getSubTasks().add(subTask.getId());

            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла " + file.getName() + ": " + e.getMessage());
        }
        return managerRestored;
    }

    private static Task fromString(String value) {
        String[] linesTask = value.split(",", 6);
            Integer id = Integer.parseInt(linesTask[0]);
            String name = linesTask[2];
            TasksType tasksType = TasksType.valueOf(linesTask[1]);
            TaskStatus status = TaskStatus.valueOf(linesTask[3]);
            String description = linesTask[4];

            switch (tasksType) {
                case TASK:
                    return new Task(id, name, description, status);

                case EPIC:
                    return new Epic(id, name, description);

                case SUBTASK:
                    String epicIdString = linesTask[5].trim();
                    if (epicIdString.isEmpty()) {
                        throw new NumberFormatException("Epic ID отсутствует для SubTask");
                    }
                    return new SubTask(id, name, description, status, Integer.valueOf(epicIdString));

                default:
                    throw new RuntimeException("Ошибка: Неподдерживаемый тип задачи." );
            }

    }

    @Override
    public int generatorId() {
        save();
        return super.generatorId();

    }

    @Override
    public ArrayList<SubTask> getEpicSubTask(int id) {
        save();
        return super.getEpicSubTask(id);

    }

    @Override
    public Task addNewTask(Task task) {
        Task newTask = super.addNewTask(task);
        save();
        return newTask;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        Epic newEpic = super.addNewEpic(epic);
        save();
        return newEpic;

    }

    @Override
    public SubTask addNewSubTask(SubTask subTask) {
        SubTask newSubTask = super.addNewSubTask(subTask);
        save();
        return newSubTask;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();

    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();

    }

    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();

    }

    public void deleteTaskId(int taskId) {
        super.deleteTaskId(taskId);
        save();

    }

    public void deleteSubTaskId(int id) {
        super.deleteSubTaskId(id);
        save();

    }

    public void deleteEpicId(int id) {
        super.deleteEpicId(id);
        save();

    }

    public void deleteTasks() {
        super.deleteTasks();
        save();

    }

    public void deleteEpics() {
        super.deleteEpics();
        save();

    }

    public void deleteSubTasks() {
        super.deleteSubTasks();
        save();

    }

}
