package ua.edu.sumdu.j2se.levchenko.tasks;

public abstract class TaskList implements Iterable<Task> {
    protected int count = 0;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract Task getTask(int index);

    public int size() {
        return count;
    }
}
