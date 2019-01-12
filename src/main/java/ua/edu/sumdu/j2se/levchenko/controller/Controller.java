package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.collections.ObservableList;
import ua.edu.sumdu.j2se.levchenko.TaskView;

public interface Controller {
    void setTaskTableObservableList(ObservableList<TaskView> taskTableObservableList);
    void show();
}
