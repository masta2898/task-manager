package ua.edu.sumdu.j2se.levchenko.tasks;

import java.io.File;
import java.util.Date;

public class TaskRepository implements Repository {
    private TaskList tasks;

    public TaskRepository() {
        this(new LinkedTaskList());
    }

    TaskRepository(TaskList tasks) {
        load(tasks);
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public void remove(Task task) {

    }

    @Override
    public void clear() {

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
        return tasks;
    }

    @Override
    public TaskList getTasksByTime(Date time) {
        return null;
    }

    @Override
    public void loadFromFile(File file) {

    }

    @Override
    public void saveToFile(File file) {

    }
}
