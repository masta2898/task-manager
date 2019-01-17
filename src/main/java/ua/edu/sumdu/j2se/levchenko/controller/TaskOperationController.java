package ua.edu.sumdu.j2se.levchenko.controller;

import ua.edu.sumdu.j2se.levchenko.tasks.Task;

public abstract class TaskOperationController extends Controller {
    protected Task task;

    void setTask(Task task) {
        this.task = task;
    }

    Task getTask() {
        return task;
    }
}
