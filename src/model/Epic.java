package model;

import java.util.ArrayList;
import java.util.Objects;


public class Epic extends Task {


    private ArrayList<Integer> subTasks = new ArrayList<>();;

    public Epic(int id, String name, String description)  {
        super(id, name, description, TaskStatus.NEW);

    }

    public Epic(String name, String description)  {
        super(name, description, TaskStatus.NEW);

    }

    public void addSubTaskId(int id){
        subTasks.add(id);
    }

    public ArrayList<Integer>getSubTasks(){
        return subTasks;

    }

    public void cleanSubTaskIds(){
        subTasks.clear();

    }

    public void removeSubTask(int id){
        subTasks.remove(Integer.valueOf(id));

    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Epic epic = (Epic) object;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return "model.Epic{" +
                "Имя задачи ='" + name + '\'' +
                ", Описание задачи ='" + description + '\'' +
                ", Номер задачи =" + id +
                ", Статус=" + status +
                '}';
    }

}
