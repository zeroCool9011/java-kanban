import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

    TaskManager manager = new TaskManager();

    Task task1 = new Task("Task #1","Task1 description", TaskStatus.NEW);
    Task task2 = new Task("Task #2","Task2 description", TaskStatus.IN_PROGRESS);
    final int id1 = manager.addNewTask(task1).getId();
    final int id2 = manager.addNewTask(task2).getId();

    Epic epic1 =  new Epic("Epic#1","Epic1 description",TaskStatus.NEW);
    Epic epic2 =  new Epic("Epic#2","Epic2 description",TaskStatus.NEW);
    final int epicId1 = manager.addNewEpic(epic1).getId();
    final int epicId2 = manager.addNewEpic(epic2).getId();

    SubTask subTask1 = new SubTask("SubTask#1","SubTask1 description",TaskStatus.IN_PROGRESS,epicId1);
    SubTask subTask2 = new SubTask("SubTask#2","SubTask2 description",TaskStatus.NEW, epicId1);
    SubTask subTask3 = new SubTask("SubTask#3","SubTask3 description",TaskStatus.DONE, epicId2);
        final int subTaskId1 = manager.addNewSubTask(subTask1).getId();
        final int subTaskId2 = manager.addNewSubTask(subTask2).getId();
        final int subTaskId3 = manager.addNewSubTask(subTask3).getId();






        manager.deleteEpicId(4);
        System.out.println(manager.getTasks());
        System.out.println("");
        System.out.println(manager.getEpics());
        System.out.println("");

        SubTask subTask = manager.getSubTask(subTaskId3);
       // subTask.setStatus(TaskStatus.IN_PROGRESS);
        //manager.updateSubTask(subTask);
        System.out.println(  manager.getEpicSubTask(3) + " " );

       // manager.deleteSubTasks();
//        manager.deleteEpics();
//        manager.printAllSubTasks();
//        System.out.println(manager.getEpics());




    final Task task = manager.getTask(2);
    task.setStatus(TaskStatus.DONE);
    manager.updateTask(task);
    System.out.println("CHANGE STATUS: Task2 IN_PROGRESS->DONE");
        System.out.println( "Sanayw: ");
   for (Task t : manager.getTasks ()) {
       System.out.println(t);
 }
    }
    public static void printAllTask(TaskManager manager){
        manager.getTasks();
        manager.getEpics();
        manager.getSubTasks();
    }
}