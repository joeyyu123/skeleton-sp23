import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private node sentinel;
    private int size;
    private class node {
        public T item;
        public node next;
        public node prev;
        public node(T value, node nextNode, node previosNode){
            item = value;
            next = nextNode;
            prev = previosNode;
        }
    }
    public LinkedListDeque() {
        size = 0;
        sentinel = new node(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(2);
        lld.addFirst(1);
        lld.addLast(5);
        lld.toList();
    }

    @Override
    public void addFirst(T x) {
        node newNode, originNode;
        originNode = sentinel.next;
        newNode = new node(x, originNode, sentinel);
        sentinel.next = newNode;
        originNode.prev = newNode;
        size += 1;

    }

    @Override
    public void addLast(T x) {
        node newNode, originNode;
        originNode = sentinel.prev;
        newNode = new node(x, sentinel,originNode);
        originNode.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        node temp;
        temp = sentinel.next;
        for (int i = 0; i < size; i++){
            returnList.addLast(temp.item);
            temp = temp.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()){
            return null;
        }
        node originFirst,first;
        originFirst = sentinel.next;
        first = sentinel.next.next;
        sentinel.next = first;
        first.prev = sentinel;
        size -= 1;
        return originFirst.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()){
            return null;
        }
        node last,originLast;
        originLast = sentinel.prev;
        last = sentinel.prev.prev;
        sentinel.prev = last;
        last.next = sentinel;
        size -= 1;
        return originLast.item;

    }

    @Override
    public T get(int index) {
        node temp;
        temp = sentinel.next;
        if (index < 0 || index >= size) {
            return null;
        }

        for (int i = 0; i < index; i++){
            temp = temp.next;
        }
        return temp.item;
    }

    @Override
    public T getRecursive(int index) {

        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, node now) {
        if (index == 0) {
            return now.item;
        }
        return getRecursiveHelper(index - 1, now.next);
    }
}
