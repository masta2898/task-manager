package ua.edu.sumdu.j2se.levchenko.tasks.repository;

import org.apache.log4j.Logger;

import ua.edu.sumdu.j2se.levchenko.tasks.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

public class TaskRepository implements Repository {
    private TaskList tasks;

    private final Logger log = Logger.getLogger(TaskRepository.class);

    public TaskRepository() {
        this(new LinkedTaskList());
    }

    TaskRepository(TaskList tasks) {
        load(tasks);
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
        log.debug(String.format("Added task %s", task.toString()));
    }

    @Override
    public void remove(Task task) {
        tasks.remove(task);
        log.debug(String.format("Removed task %s", task.toString()));
    }

    @Override
    public void clear() {
        log.debug(String.format("Cleared %d task(s).", this.tasks.size()));
        tasks = new LinkedTaskList();
        System.gc(); // collect old tasks list.
    }

    @Override
    public void load(TaskList tasks) {
        this.tasks = tasks;
        log.debug(String.format("Loaded %d task(s).", this.tasks.size()));
    }

    @Override
    public Task getById(int id) {
        return tasks.getTask(id);
    }

    @Override
    public TaskList getAll() {
        return tasks;
    }

    @Override
    public TaskList getTasksByTime(Date time) {
        // todo: get tasks from period, and notify about them once in 10 minutes.
        LinkedTaskList tasks = new LinkedTaskList();
        for (Task task: this.tasks) {
            if (!task.isRepeated()) {
                if (task.getTime().equals(time)) {
                    tasks.add(task);
                }
                continue;
            }

            var repeatedTasks = Tasks.calendar(this.tasks, task.getStartTime(), task.getEndTime());
            if (repeatedTasks.containsKey(time)) {
                for (Task repeatedTask: repeatedTasks.get(time)) {
                    tasks.add(repeatedTask);
                }
            }
        }
        log.debug(String.format("Got %d task(s) by time %s", tasks.size(), time.toString()));
        return tasks;
    }

    @Override
    public void loadFromFile(File file) throws RepositoryException {
        try {
            TaskIO.readText(this.tasks, file);
            log.info(String.format("Loaded %d task(s) from %s", this.tasks.size(), file.getAbsolutePath()));
        } catch (IOException e) {
            log.error(String.format("Error happened trying to read file %s", file.getAbsolutePath()), e);
            RepositoryException exception = new RepositoryException("Error reading file.");
            exception.addSuppressed(e);
            throw exception;
        } catch (ParseException e) {
            log.error(String.format("Error happened trying to parse file %s", file.getAbsolutePath()), e);
            RepositoryException exception = new RepositoryException("Error parsing file.");
            exception.addSuppressed(e);
            throw exception;
        } catch (Exception e) {
            log.error(String.format("Error happened trying to work with file %s", file.getAbsolutePath()), e);
            RepositoryException exception = new RepositoryException("Error working with file.");
            exception.addSuppressed(e);
            throw exception;
        }
    }

    @Override
    public void saveToFile(File file) throws RepositoryException {
        try {
            TaskIO.writeText(this.tasks, file);
            log.info(String.format("Saved %d task(s) to %s", this.tasks.size(), file.getAbsolutePath()));
        } catch (IOException e) {
            log.error(String.format("Error happened trying to write to file %s", file.getAbsolutePath()), e);
            RepositoryException exception = new RepositoryException("Error writing to file.");
            exception.addSuppressed(e);
            throw exception;
        }
    }
}
