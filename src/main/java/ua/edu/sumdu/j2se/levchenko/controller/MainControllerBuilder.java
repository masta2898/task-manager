package ua.edu.sumdu.j2se.levchenko.controller;

import ua.edu.sumdu.j2se.levchenko.notificator.Notificator;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;

public class MainControllerBuilder {
    private Repository taskRepository;
    private Controller aboutController;
    private TasksController tasksPeriodController;
    private TaskOperationController editTaskController;
    private TaskOperationController taskDetailsController;

    public MainControllerBuilder setTaskRepository(Repository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    public MainControllerBuilder setAboutController(Controller aboutController) {
        this.aboutController = aboutController;
        return this;
    }

    public MainControllerBuilder setTasksPeriodController(TasksController tasksPeriodController) {
        this.tasksPeriodController = tasksPeriodController;
        return this;
    }

    public MainControllerBuilder setEditTaskController(TaskOperationController editTaskController) {
        this.editTaskController = editTaskController;
        return this;
    }

    public MainControllerBuilder setTaskDetailsController(TaskOperationController taskDetailsController) {
        this.taskDetailsController = taskDetailsController;
        return this;
    }

    public MainController createMainController() {
        return new MainController(taskRepository, aboutController, editTaskController, taskDetailsController, tasksPeriodController);
    }
}