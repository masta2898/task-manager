package ua.edu.sumdu.j2se.levchenko.tasks.repository;

import ua.edu.sumdu.j2se.levchenko.tasks.Task;
import ua.edu.sumdu.j2se.levchenko.tasks.TaskList;

import java.io.File;
import java.util.Date;

public interface Repository {
    void add(Task task);
    void remove(Task task);

    void clear();
    void load(TaskList tasks);

    Task getById(int id);
    TaskList getAll();
    TaskList getTasksByPeriod(Date time, Date to);

    void loadFromFile(File file) throws RepositoryException;
    void saveToFile(File file) throws RepositoryException;
}
