package ua.edu.sumdu.j2se.levchenko.controller;

import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;

public abstract class TasksController extends Controller {
    protected Repository repository;

    void setRepository(final Repository repository) {
        this.repository = repository;
    }
    
    Repository getRepository() {
        return repository;
    }
}
