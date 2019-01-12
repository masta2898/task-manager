package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.levchenko.TaskView;

public class AboutController implements Controller {
    private Stage aboutWindow;
    private ObservableList<TaskView> taskTableObservableList;

    public AboutController(Stage aboutWindow) {
        this.aboutWindow = aboutWindow;
    }

    @Override
    public void setTaskTableObservableList(ObservableList<TaskView> taskTableObservableList) {
        this.taskTableObservableList = taskTableObservableList;
    }

    @Override
    public void show() {
        aboutWindow.showAndWait();
    }

    @FXML
    void close() {
        aboutWindow.close();
    }
}
