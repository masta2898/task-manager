package ua.edu.sumdu.j2se.levchenko.controller;

import ua.edu.sumdu.j2se.levchenko.tasks.Task;

public interface TaskOperationController extends Controller {
    void setTask(Task task);
    Task getTask();
}
