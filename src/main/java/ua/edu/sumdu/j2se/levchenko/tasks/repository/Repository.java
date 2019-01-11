package ua.edu.sumdu.j2se.levchenko.tasks;

import java.io.File;
import java.util.Date;

public interface Repository {
    void add(Task task);
    void remove(Task task);

    void clear();
    void load(TaskList tasks);

    Task getById(int id);
    TaskList getAll();
    TaskList getTasksByTime(Date time);

    void loadFromFile(File file);
    void saveToFile(File file);
}
