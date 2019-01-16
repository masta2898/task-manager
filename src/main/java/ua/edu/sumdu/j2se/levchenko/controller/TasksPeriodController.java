package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.levchenko.TaskView;
import ua.edu.sumdu.j2se.levchenko.tasks.Task;
import ua.edu.sumdu.j2se.levchenko.tasks.TaskList;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;

import static ua.edu.sumdu.j2se.levchenko.controller.ControllerHelper.getDate;
import static ua.edu.sumdu.j2se.levchenko.controller.ControllerHelper.taskModelToView;

public class TasksPeriodController implements TasksController {
    @FXML
    private DatePicker from;
    @FXML
    private DatePicker to;

    @FXML
    private TableView<TaskView> taskTable;

    @FXML
    private TableColumn active;
    @FXML
    private TableColumn title;
    @FXML
    private TableColumn repeated;
    @FXML
    private TableColumn time;
    @FXML
    private TableColumn start;
    @FXML
    private TableColumn end;
    @FXML
    private TableColumn interval;

    private ObservableList<TaskView> taskTableObservableList = FXCollections.observableArrayList();

    private Stage tasksPeriodWindow;
    private Repository taskRepository;

    @Override
    public void setRepository(final Repository repository) {
        taskRepository = repository;
    }

    @Override
    public void setWindow(Stage window) {
        tasksPeriodWindow = window;
    }

    @Override
    public void showWindow() {
        clearForm();

        active.setCellValueFactory(new PropertyValueFactory<>("active"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        repeated.setCellValueFactory(new PropertyValueFactory<>("repeated"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        interval.setCellValueFactory(new PropertyValueFactory<>("interval"));
        taskTable.setItems(taskTableObservableList);

        tasksPeriodWindow.setResizable(false);
        tasksPeriodWindow.showAndWait();
    }

    @FXML
    void apply(ActionEvent event) {
        if (from.getValue() == null || to.getValue() == null || taskRepository == null) {
            return;
        }

        if (from.getValue().isBefore(to.getValue())) {
            taskTableObservableList.clear();
            TaskList tasks = taskRepository.getTasksByPeriod(getDate(from), getDate(to));
            for (Task task: tasks) {
                taskTableObservableList.add(taskModelToView(task));
            }
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        tasksPeriodWindow.close();
    }

    private void clearForm() {
        from.setValue(null);
        to.setValue(null);
        taskTableObservableList.clear();
    }
}
