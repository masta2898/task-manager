package ua.edu.sumdu.j2se.levchenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import ua.edu.sumdu.j2se.levchenko.controller.AboutController;
import ua.edu.sumdu.j2se.levchenko.controller.Controller;
import ua.edu.sumdu.j2se.levchenko.controller.MainController;
import ua.edu.sumdu.j2se.levchenko.controller.TaskOperationController;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.TaskRepository;

import java.io.IOException;

public class TaskManager extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) throws Exception {
        Controller mainController = new MainController(mainWindow, getTaskOperationController(), getAboutController(),
                new TaskRepository());

        FXMLLoader loader = new FXMLLoader();
        loader.setController(mainController);
        loader.setLocation(getClass().getResource("/main.fxml"));
        Parent content = loader.load();

        mainWindow.setTitle("Task Manager");
        mainWindow.setResizable(true);
        mainWindow.setScene(new Scene(content));

        mainController.show();
    }

    // todo: refactor code duplicates
    private Controller getAboutController() throws IOException {
        Stage aboutWindow = new Stage();
        Controller aboutController = new AboutController(aboutWindow);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/about.fxml"));
        loader.setController(aboutController);
        Parent parent = loader.load();

        aboutWindow.initModality(Modality.APPLICATION_MODAL);
        aboutWindow.setTitle("About");
        aboutWindow.setScene(new Scene(parent));
        return aboutController;
    }

    private Controller getTaskOperationController() throws IOException {
        Stage taskOperationWindow = new Stage();
        Controller taskOperationController = new TaskOperationController(taskOperationWindow);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/task.fxml"));
        loader.setController(taskOperationController);
        Parent parent = loader.load();

        taskOperationWindow.initModality(Modality.APPLICATION_MODAL);
        taskOperationWindow.setTitle("Task Operation");
        taskOperationWindow.setScene(new Scene(parent));
        return taskOperationController;
    }
}
