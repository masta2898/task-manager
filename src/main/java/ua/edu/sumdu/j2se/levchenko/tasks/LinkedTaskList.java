package ua.edu.sumdu.j2se.levchenko.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedTaskList extends TaskList implements Cloneable {
    private Task value;
    private LinkedTaskList head;
    private LinkedTaskList tail;
    private LinkedTaskList next;

    public LinkedTaskList() {
    }

    public LinkedTaskList getHead() {
        return head;
    }

    public void setHead(LinkedTaskList head) {
        this.head = head;
    }

    public Task getValue() {
        return value;
    }

    public void setValue(Task value) {
        this.value = value;
    }

    public LinkedTaskList getTail() {
        return tail;
    }

    public void setTail(LinkedTaskList tail) {
        this.tail = tail;
    }

    public LinkedTaskList getNext() {
        return next;
    }

    public void setNext(LinkedTaskList next) {
        this.next = next;
    }

    public LinkedTaskList(Task task) {
        value = task;
    }

    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException("You can't add null element");
        }

        LinkedTaskList node = new LinkedTaskList(task);

        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }

        tail = node;
        count++;
    }

    public boolean remove(Task task) {
        LinkedTaskList previous = null;
        LinkedTaskList current = head;

        while (current != null) {
            if (current.value.equals(task)) {
                if (previous != null) {
                    previous.next = current.next;

                    if (current.next == null) {
                        tail = previous;
                    }
                } else {
                    head = head.next;

                    if (head == null) {
                        tail = null;
                    }
                }
                count--;

                return true;
            }
            previous = current;
            current = current.next;
        }

        return false;
    }

    public Task getTask(int index) {
        if (index > count - 1) {
            throw new IllegalArgumentException("You want element" + index + ", but the last index is " + (count - 1));
        }

        LinkedTaskList previous = null;
        LinkedTaskList current = head;
        int i = 0;

        while (index != i) {
            previous = current;
            current = current.next;
            i++;
        }

        return current.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        LinkedTaskList other = (LinkedTaskList) obj;

        LinkedTaskList current = head;
        LinkedTaskList current2 = other.head;

        while (current != null) {
            if (!current.value.equals(current2.value)) {
                return false;
            }

            current = current.next;
            current2 = current2.next;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        LinkedTaskList current = head;

        while (current != null) {
            result = 31 * result + current.value.hashCode();

            current = current.next;
        }

        return result;
    }

    @Override
    public LinkedTaskList clone() {
        LinkedTaskList copiedOne = new LinkedTaskList();

        LinkedTaskList current = head;
        LinkedTaskList current2 = new LinkedTaskList();

        while (current != null) {
            current2.value = current.value;
            copiedOne.add(current2.value);
            current = current.next;
        }

        return copiedOne;
    }

    @Override
    public String toString() {
        return "This is LinkedTaskList {" +
                "value = " + value +
                ", head = " + head +
                ", tail = " + tail +
                ", next = " + next +
                '}';
    }

    @Override
    public Iterator<Task> iterator() {
        return new MyIterator<>();
    }

    private class MyIterator<Task> implements Iterator<Task> {

        private LinkedTaskList current;
        private LinkedTaskList next;
        private boolean callNext;

        public MyIterator() {
            current = null;
            next = getHead();
            callNext = false;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Task next() {
            if (next == null) {
                throw new NoSuchElementException();
            }

            Task temp = (Task) next.getValue();
            current = next;
            next = next.getNext();
            callNext = true;
            return temp;
        }

        @Override
        public void remove() {
            if (!callNext) {
                throw new IllegalStateException();
            }

            LinkedTaskList.this.remove(current.getValue());

            callNext = false;
        }
    }
}

