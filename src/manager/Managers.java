package manager;

import java.io.File;

public class  Managers {
    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new File("./resources/task.csv"));
        // return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
