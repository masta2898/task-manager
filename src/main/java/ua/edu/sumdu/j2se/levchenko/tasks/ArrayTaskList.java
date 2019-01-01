package ua.edu.sumdu.j2se.levchenko.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends TaskList implements Cloneable {
    private Task[] arrayTask = new Task[3];

    public ArrayTaskList() {
    }

    public ArrayTaskList(int length) {
        arrayTask = new Task[length];
    }

    private void growArray() {
        int newLength = (arrayTask.length * 3) / 2 + 1;

        Task[] newArray = new Task[newLength];

        System.arraycopy(arrayTask, 0, newArray, 0, arrayTask.length);

        arrayTask = newArray;
    }

    public void add(Task task) {
        if (arrayTask.length == count) {
            growArray();
        }

        if (task == null) {
            throw new NullPointerException("You can't add null element");
        }

        arrayTask[count] = task;
        count++;
    }

    public boolean remove(Task task) {
        for (int i = 0; i < count; i++) {
            if (arrayTask[i].equals(task)) {
                int shiftStart = i + 1;

                if (shiftStart < count) {
                    System.arraycopy(arrayTask, shiftStart, arrayTask, i, count - shiftStart);
                }
                count--;
                return true;
            }
        }

        return false;
    }

    public Task getTask(int index) {
        if (index > count - 1) {
            throw new IllegalArgumentException("You want element" + index + ", but the last index is " + (count - 1));
        }

        return arrayTask[index];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        ArrayTaskList other = (ArrayTaskList) obj;

        for (int i = 0; i < count; i++) {
            if (!arrayTask[i].equals(other.arrayTask[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        for (int i = 0; i < count; i++) {
            result = 31 * result + arrayTask[i].hashCode();
        }

        return result;
    }

    @Override
    public ArrayTaskList clone() {
        Task[] copiedElems = new Task[count];
        ArrayTaskList copiedOne = new ArrayTaskList();

        for (int i = 0; i < count; i++) {
            copiedElems[i] = arrayTask[i];
            copiedOne.add(copiedElems[i]);
        }

        return copiedOne;
    }

    @Override
    public String toString() {
        return "This is ArrayTaskList {" +
                "arrayTask = " + java.util.Arrays.toString(arrayTask) +
                '}';
    }

    @Override
    public Iterator<Task> iterator() {
        return new Itr<>();
    }

    private class Itr<Task> implements Iterator<Task> {
        int cursor;
        int lastRet = -1;

        Itr() {
        }

        public boolean hasNext() {
            return cursor != count;
        }

        public Task next() {
            int i = cursor;
            if (i >= count) {
                throw new NoSuchElementException();
            }

            cursor = i + 1;
            return (Task) arrayTask[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            ArrayTaskList.this.remove(arrayTask[lastRet]);
            cursor = lastRet;
            lastRet = -1;
        }
    }
}

