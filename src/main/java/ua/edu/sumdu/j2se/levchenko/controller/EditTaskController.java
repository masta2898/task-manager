package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import ua.edu.sumdu.j2se.levchenko.tasks.Task;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditTaskController implements TaskOperationController {
    @FXML
    private TextField title;
    @FXML
    private CheckBox active;
    @FXML
    private CheckBox repeated;
    @FXML
    private DatePicker time;
    @FXML
    private Spinner<Integer> interval;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;

    private Task task;
    private Stage editTaskWindow;

    @Override
    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public Task getTask() {
        return task;
    }

    @Override
    public void setWindow(Stage window) {
        this.editTaskWindow = window;
    }

    @Override
    public void showWindow() {
        clearForm();

        if (task != null) {
            title.setText(task.getTitle());
            active.setSelected(task.isActive());
            if (task.isRepeated()) {
                start.setValue(getLocalDate(task.getStartTime()));
                end.setValue(getLocalDate(task.getEndTime()));
                interval.getValueFactory().setValue(task.getRepeatInterval());
            } else {
                time.setValue(getLocalDate(task.getTime()));
            }
        }

        setRepeated(new ActionEvent());
        this.editTaskWindow.showAndWait();
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
            int interval = this.interval.getValue();
            if (start != null && end != null) {
                task = new Task(title, start, end, interval);
                task.setActive(active.isSelected());
            }
        } else {
            Date time = getDate(this.time);
            if (time != null) {
                task = new Task(title, time);
                task.setActive(active.isSelected());
            }
        }

        this.editTaskWindow.close();
    }

    @FXML
    void cancel(ActionEvent event) {
        this.editTaskWindow.close();
    }

    @FXML
    void setRepeated(ActionEvent event) {
        boolean isRepeated = repeated.isSelected();

        start.setDisable(!isRepeated);
        end.setDisable(!isRepeated);
        interval.setDisable(!isRepeated);

        time.setDisable(isRepeated);
    }

    private Date getDate(DatePicker view) {
        LocalDate localDate = view.getValue();
        if (localDate != null) {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            return Date.from(instant);
        }
        return null;
    }

    private LocalDate getLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private void clearForm() {
        title.clear();
        active.setSelected(false);
        repeated.setSelected(false);
        time.setValue(null);
        start.setValue(null);
        end.setValue(null);
        interval.getValueFactory().setValue(0);
    }
}
