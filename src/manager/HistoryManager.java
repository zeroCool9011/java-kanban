package manager;

import model.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void addHistory(Task task);

    void remove(int id);

    ArrayList<Task> getHistory();

}
