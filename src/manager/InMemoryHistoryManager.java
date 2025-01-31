package manager;

import model.Task;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;

    private static class Node {

        Task task;
        Node prev;
        Node next;

        private Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }

    private ArrayList<Task> getTasks() {

        ArrayList<Task> tasks = new ArrayList<>();
        Node node = head;

        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }
        return tasks;
    }

    private void linkedLast(Task task) {

        final Node node = new Node(task, null, null);

        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
    }

    @Override
    public void addHistory(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            Node node = nodeMap.remove(task.getId());
            removeNode(node);
        }
        linkedLast(task);
        nodeMap.put(task.getId(), tail);
    }

    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            Node node = nodeMap.remove(id);
            removeNode(node);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(getTasks());

    }

    private void removeNode(Node node) {
        final Node prev = node.prev;
        final Node next = node.next;
        if (prev == null) {
            head = next;

        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;

        } else {
            next.prev = prev;
            node.next = null;
        }
    }
}