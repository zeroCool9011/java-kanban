package manager;

import model.Task;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final int MAX_HISTORY = 10;
    private ArrayList<Task> receivedTasks;


    public InMemoryHistoryManager() {
        receivedTasks = new ArrayList<>();

    }

    @Override
    public void addHistory(Task task) {
        if (task == null){
            return ;
        }else {
            if (receivedTasks.size() >= MAX_HISTORY) {
                receivedTasks.remove(0);

            }
            receivedTasks.add(task);
        }
    }

    @Override
    public ArrayList<Task> getHistory(){
        return new ArrayList<>(receivedTasks);

    }


}