package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TaskDetailsController extends TaskOperationController {
    @FXML
    private Label title;

    @FXML
    private HBox timeBox;
    @FXML
    private Label time;

    @FXML
    private HBox repeatParametersBox;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label interval;

    @FXML
    private CheckBox active;
    @FXML
    private CheckBox repeated;

    @Override
    public void showWindow() {
        if (task == null) {
            return;
        }

        title.setText(task.getTitle());

        boolean taskIsRepeated = task.isRepeated();
        repeated.setSelected(taskIsRepeated);
        repeatParametersBox.setVisible(taskIsRepeated);
        timeBox.setVisible(!taskIsRepeated);
        active.setSelected(task.isActive());

        if (!taskIsRepeated) {
            time.setText(task.getTime().toString());
        } else {
            start.setText(task.getStartTime().toString());
            end.setText(task.getEndTime().toString());
            interval.setText(String.valueOf(task.getRepeatInterval()));
        }

        window.setResizable(false);
        window.showAndWait();
    }
}
