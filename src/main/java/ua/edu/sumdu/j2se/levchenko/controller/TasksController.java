package ua.edu.sumdu.j2se.levchenko.controller;

import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;

public interface TaskPeriodController extends Controller {
    void setRepository(final Repository repository);
}
