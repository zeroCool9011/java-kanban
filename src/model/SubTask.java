package model;

import java.util.Objects;

public class SubTask extends Task {
protected int epicId;



        public SubTask (int id, String name, String description, TaskStatus status, int epicId){
            super(id, name, description, status);
            this.epicId = 0;

        }

    public SubTask (String name, String description, TaskStatus status, int epicId){
        super(name, description, status);
        this.epicId = epicId;

    }

    public int getEpicId() {

            return epicId;
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
        return "model.SubTask{" +
                "Имя задачи ='" + name + '\'' +
                ", Описание задачи ='" + description + '\'' +
                ", Номер задачи =" + id +
                ", Статус=" + status +
                ", Эпик ИД=" + epicId +
                '}';
    }
}
