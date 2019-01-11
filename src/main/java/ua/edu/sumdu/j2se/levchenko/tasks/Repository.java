package ua.edu.sumdu.j2se.levchenko.tasks;

import java.util.Date;

public interface Repository {
    void add(Task task);
    void remove(Task task);

    void erase();
    void load(TaskList tasks);

    Task getById(int id);
    TaskList getAll();
    TaskList getTasksByTime(Date time);
}
