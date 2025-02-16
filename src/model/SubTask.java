package model;

import manager.TasksType;

import java.util.Objects;

public class SubTask extends Task {
    protected int epicId;


    public SubTask(int id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;

    }

    public SubTask(String name, String description, TaskStatus status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;

    }

    public int getEpicId() {

        return epicId;
    }

    @Override
    public TasksType getType() {
        return TasksType.SUBTASK;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        SubTask subTask = (SubTask) object;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return id + "," + getType() + "," + name + "," + status + "," + description + "," + epicId;
    }
}
