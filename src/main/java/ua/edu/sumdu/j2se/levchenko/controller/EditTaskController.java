package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import ua.edu.sumdu.j2se.levchenko.tasks.Task;

import java.util.Date;

import static ua.edu.sumdu.j2se.levchenko.controller.ControllerHelper.getDate;
import static ua.edu.sumdu.j2se.levchenko.controller.ControllerHelper.getLocalDate;

public class EditTaskController extends TaskOperationController {
    @FXML
    private TextField title;

    @FXML
    private CheckBox active;
    @FXML
    private CheckBox repeated;

    @FXML
    private DatePicker time;
    @FXML
    private Spinner<Integer> timeHours;
    @FXML
    private Spinner<Integer> timeMinutes;

    @FXML
    private Spinner<Integer> interval;
    @FXML
    private DatePicker start;
    @FXML
    private Spinner<Integer> startHours;
    @FXML
    private Spinner<Integer> startMinutes;
    @FXML
    private DatePicker end;
    @FXML
    private Spinner<Integer> endHours;
    @FXML
    private Spinner<Integer> endMinutes;

    @Override
    public void showWindow() {
        clearForm();
        if (task != null) {
            fillForm();
        }

        setRepeated(new ActionEvent());
        window.showAndWait();
    }

    @FXML
    void save(ActionEvent event) {
        String title = this.title.getText();
        if (title == null) {
            return;
        }

        if (repeated.isSelected()) {
            Date start = getDate(this.start);
            Date end = getDate(this.end);
            if (start != null && end != null) {
                if (start.after(end)) {
                    return;
                }

                start.setHours(startHours.getValue());
                start.setMinutes(startMinutes.getValue());
                end.setHours(endHours.getValue());
                end.setMinutes(endMinutes.getValue());

                int interval = this.interval.getValue();
                task = new Task(title, start, end, interval);
                task.setActive(active.isSelected());
            }
        } else {
            Date time = getDate(this.time);
            if (time != null) {
                time.setHours(timeHours.getValue());
                time.setMinutes(timeMinutes.getValue());

                task = new Task(title, time);
                task.setActive(active.isSelected());
            }
        }

        window.close();
    }

    @FXML
    void cancel(ActionEvent event) {
        window.close();
    }

    @FXML
    void setRepeated(ActionEvent event) {
        boolean isRepeated = repeated.isSelected();

        start.setDisable(!isRepeated);
        end.setDisable(!isRepeated);
        interval.setDisable(!isRepeated);

        startHours.setDisable(!isRepeated);
        startMinutes.setDisable(!isRepeated);
        endHours.setDisable(!isRepeated);
        endMinutes.setDisable(!isRepeated);

        time.setDisable(isRepeated);

        timeHours.setDisable(isRepeated);
        timeMinutes.setDisable(isRepeated);
    }

    private void fillForm() {
        title.setText(task.getTitle());
        active.setSelected(task.isActive());

        boolean isRepeated = task.isRepeated();
        repeated.setSelected(isRepeated);

        if (isRepeated) {
            startHours.getValueFactory().setValue(task.getStartTime().getHours());
            startMinutes.getValueFactory().setValue(task.getStartTime().getMinutes());
            endHours.getValueFactory().setValue(task.getEndTime().getHours());
            endMinutes.getValueFactory().setValue(task.getEndTime().getMinutes());
            start.setValue(getLocalDate(task.getStartTime()));
            end.setValue(getLocalDate(task.getEndTime()));
            interval.getValueFactory().setValue(task.getRepeatInterval());
        } else {
            timeHours.getValueFactory().setValue(task.getTime().getHours());
            timeMinutes.getValueFactory().setValue(task.getTime().getMinutes());
            time.setValue(getLocalDate(task.getTime()));
        }
    }

    private void clearForm() {
        title.clear();

        active.setSelected(false);
        repeated.setSelected(false);

        time.setValue(null);
        start.setValue(null);
        end.setValue(null);

        interval.getValueFactory().setValue(0);
        timeHours.getValueFactory().setValue(0);
        timeMinutes.getValueFactory().setValue(0);
        startHours.getValueFactory().setValue(0);
        startMinutes.getValueFactory().setValue(0);
        endHours.getValueFactory().setValue(0);
        endMinutes.getValueFactory().setValue(0);
    }
}
