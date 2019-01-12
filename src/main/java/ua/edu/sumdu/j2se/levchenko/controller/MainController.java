package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import ua.edu.sumdu.j2se.levchenko.TaskView;
import ua.edu.sumdu.j2se.levchenko.tasks.*;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.RepositoryException;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable, Controller {
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

    @FXML
    private Label status;

    private Stage mainWindow;
    private File tasksFile;
    private boolean fileChanged = false;
    private final Repository taskRepository;
    private ObservableList<TaskView> taskTableObservableList = FXCollections.observableArrayList();

    private TaskOperationController newTaskController;
    private TaskOperationController editTaskController;
    private TaskOperationController taskDetailsController;

    private Controller aboutController;

    public MainController(Repository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void setNewTaskController(TaskOperationController newTaskController) {
        this.newTaskController = newTaskController;
    }

    public void setEditTaskController(TaskOperationController editTaskController) {
        this.editTaskController = editTaskController;
    }

    public void setTaskDetailsController(TaskOperationController taskDetailsController) {
        this.taskDetailsController = taskDetailsController;
    }

    public void setAboutController(Controller aboutController) {
        this.aboutController = aboutController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        status.setText("Task manager loaded.");
        active.setCellValueFactory(new PropertyValueFactory<>("active"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        repeated.setCellValueFactory(new PropertyValueFactory<>("repeated"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        interval.setCellValueFactory(new PropertyValueFactory<>("interval"));
        taskTable.setItems(taskTableObservableList);
    }

    @Override
    public void setWindow(Stage window) {
        this.mainWindow = window;
    }

    @Override
    public void showWindow() {
        mainWindow.show();
    }

    @FXML
    void openTasksFile(ActionEvent event) {
        if (fileHasChanged()) {
            saveTasks(event);
        }

        File tasksFile = showOpenTasksFile();
        if (tasksFile == null) {
            return;
        }

        openTasksFile(tasksFile);
    }

    @FXML
    void saveTasks(ActionEvent event) {
        if (!fileChanged) {
            return;
        }

        tasksFile = tasksFile == null ? showSaveTasksFile() : tasksFile;
        if (tasksFile == null) {
            return;
        }

        saveTasksFile(tasksFile);
    }

    @FXML
    void saveTasksAs(ActionEvent event) {
        File tasksFile = showSaveTasksFile();
        if (tasksFile == null) {
            return;
        }

        saveTasksFile(tasksFile);
    }

    @FXML
    void close(ActionEvent event) {
        if (fileHasChanged()) {
            saveTasks(event);
        }
        mainWindow.close();
    }

    @FXML
    void newTask(ActionEvent event) {
        if (newTaskController != null) {
            newTaskController.showWindow();
            Task task = newTaskController.getTask();
            if (task != null) {
                taskRepository.add(task);
                taskTableObservableList.add(taskModelToView(task));
                fileChanged = true;
                status.setText(String.format("Created '%s' task.", task.getTitle()));
            }
        }
    }

    @FXML
    void editTask(ActionEvent event) {
        TaskView selectedTaskView = getSelectedTaskView();
        Task selectedTask = getSelectedTask(selectedTaskView);
        if (selectedTask == null) {
            return;
        }

        Task oldTask = selectedTask.clone();
        editTaskController.setTask(selectedTask);
        editTaskController.showWindow();
        Task task = editTaskController.getTask();
        if (task.equals(oldTask)) {
            return;
        }

        taskRepository.remove(oldTask);
        taskRepository.add(task);

        taskTableObservableList.remove(selectedTaskView);
        taskTableObservableList.add(taskModelToView(task));

        fileChanged = true;

        status.setText(String.format("Edited '%s' task.", task.getTitle()));
    }

    @FXML
    void deleteTask(ActionEvent event) {
        TaskView selectedTaskView = getSelectedTaskView();
        Task selectedTask = getSelectedTask(selectedTaskView);
        if (selectedTask != null) {
            taskRepository.remove(selectedTask);
            taskTableObservableList.remove(selectedTaskView);
            fileChanged = true;
            status.setText(String.format("Deleted '%s' task.", selectedTask.getTitle()));
        }
    }

    @FXML
    void showTaskDetails(ActionEvent event) {
        Task selectedTask = getSelectedTask(getSelectedTaskView());
        if (selectedTask != null) {
            if (taskDetailsController != null) {
                taskDetailsController.setTask(selectedTask);
                taskDetailsController.showWindow();
            }
        }
    }

    @FXML
    void showAbout(ActionEvent event) {
        aboutController.showWindow();
    }

    private Task taskViewToModel(TaskView view) {
        Task task;
        if (view.getRepeated().equals("Yes")) {
            task = new Task(view.getTitle(), view.getStart(), view.getEnd(), view.getInterval());
        } else {
            task = new Task(view.getTitle(), view.getTime());
        }
        task.setActive(view.getActive().equals("Yes"));
        return task;
    }

    private TaskView taskModelToView(Task task) {
        TaskView view;
        if (task.isRepeated()) {
            view = new TaskView(task.getTitle(), task.getStartTime(), task.getEndTime(),
                    task.getRepeatInterval(), task.isActive() ? "Yes" : "No");
        } else {
            view = new TaskView(task.getTitle(), task.getTime(), task.isActive() ? "Yes" : "No");
        }
        return view;
    }

    private TaskView getSelectedTaskView() {
        TableView.TableViewSelectionModel<TaskView> tableSelectionModel = taskTable.getSelectionModel();
        return tableSelectionModel != null ? tableSelectionModel.getSelectedItem() : null;
    }

    private Task getSelectedTask(TaskView selectedTaskView) {
        if (selectedTaskView == null) {
            showWarningMessage("No selected task.", "Select task to perform this operation!");
            return null;
        }
        return taskViewToModel(selectedTaskView);
    }

    private boolean fileHasChanged() {
        if (!fileChanged) return false;
        String filename = tasksFile != null ? tasksFile.getName() : "new file";
        return askToSaveFile(filename);
    }

    private void loadTasksToTable(TaskList tasks) {
        taskTableObservableList.clear();
        for (Task task: tasks)  {
            taskTableObservableList.add(taskModelToView(task));
        }
    }

    private void openTasksFile(File tasksFile) {
        taskRepository.clear();
        try {
            taskRepository.loadFromFile(tasksFile);
            TaskList tasks = taskRepository.getAll();
            loadTasksToTable(tasks);

            String filename = tasksFile.getAbsolutePath();
            status.setText(String.format("Loaded tasks from: %s", filename));
            mainWindow.setTitle(String.format("Task Manager - %s", filename));

            fileChanged = false;
            this.tasksFile = tasksFile;
        } catch (RepositoryException e) {
            String errorText = String.format("Error loading tasks from file: %s", tasksFile.getAbsolutePath());
            showErrorMessage(errorText, e.getMessage());
            status.setText(errorText);
        }
    }

    private void saveTasksFile(File tasksFile) {
        try {
            taskRepository.saveToFile(tasksFile);
            status.setText(String.format("Saved tasks to %s", tasksFile.getAbsolutePath()));
            fileChanged = false;
            this.tasksFile = tasksFile;
        } catch (RepositoryException e) {
            String errorText = String.format("Error saving tasks to file: %s", tasksFile.getAbsolutePath());
            showErrorMessage(errorText, e.getMessage());
            status.setText(errorText);
        }
    }

    private File showOpenTasksFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose tasks file.");
        return fileChooser.showOpenDialog(mainWindow);
    }

    private File showSaveTasksFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose where to save tasks.");
        return fileChooser.showSaveDialog(mainWindow);
    }

    private boolean askToSaveFile(String filename) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Do you want to save file?");
        alert.setHeaderText("Save file?");
        alert.setContentText(String.format("You have made changes in %s, do you want to save it?", filename));
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showWarningMessage(String message, String details) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(message);
        alert.setContentText(details);
        alert.setResizable(true);
        alert.showAndWait();
    }

    private void showErrorMessage(String message, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(details);
        alert.setResizable(true);
        alert.showAndWait();
    }
}
