package ua.edu.sumdu.j2se.levchenko.tasks;

import java.util.Date;

public class TaskRepository implements Repository {
    private TaskList tasks;

    TaskRepository() {
        this(new LinkedTaskList());
    }

    TaskRepository(TaskList tasks) {
        load(tasks);
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public void remove(Task task) {

    }

    @Override
    public void erase() {

    }

    @Override
    public void load(TaskList tasks) {
        this.tasks = tasks;
    }

    @Override
    public Task getById(int id) {
        return null;
    }

    @Override
    public TaskList getAll() {
        return null;
    }

    @Override
    public TaskList getTasksByTime(Date time) {
        return null;
    }
}
