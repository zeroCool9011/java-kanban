package manager;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final int MAX_HISTORY = 9;
    private ArrayList<Task> receivedTasks;


    public InMemoryHistoryManager() {
        receivedTasks = new ArrayList<>();

    }

    @Override
    public void addHistory(Task task) {
        if (receivedTasks.size() >= MAX_HISTORY) {
            receivedTasks.remove(0);

        }
        receivedTasks.add(task);
    }

    @Override
    public ArrayList<Task> getHistory(){
        return receivedTasks;

    }


}