package managers.history;

import entity.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {


    private Map<Integer, Node<Task>> historyNodeMap = new HashMap<>();
    private CustomLinkedList customLinkedList = new CustomLinkedList();


    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            customLinkedList.linkLast(task);
            historyNodeMap.put(task.getId(), customLinkedList.tail);
        }


    }

    @Override
    public void remove(int id) {
        if (historyNodeMap.containsKey(id)) {
            Node<Task> nodeToRemove = historyNodeMap.remove(id);
            customLinkedList.removeNode(nodeToRemove);
        }
    }


    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }

    private class CustomLinkedList {

        Node<Task> head;
        Node<Task> tail;
        int size = 0;


        public void linkLast(Task task) {

            Node<Task> oldTail = tail;
            Node<Task> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
            size++;

        }

        List<Task> getTasks() {
            List<Task> taskList = new ArrayList<>(size);
            Node<Task> currentNode = head;
            while (currentNode != null) {
                taskList.add(currentNode.data);
                currentNode = currentNode.next;
            }
            return taskList;
        }

        private void removeNode(Node<Task> node) {
            if (node != null) {
                if (node == head) {
                    head = node.next;
                }
                if (node == tail) {
                    tail = node.prev;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
            }
            node = null;
            size--;
        }
    }

    public static class Node<T> {

        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
