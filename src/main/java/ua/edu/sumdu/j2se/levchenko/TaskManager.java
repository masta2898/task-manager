package ua.edu.sumdu.j2se.levchenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.levchenko.controller.MainWindowController;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.TaskRepository;

public class TaskManager extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainWindowController controller = new MainWindowController(stage, new TaskRepository());

        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getResource("/main.fxml"));
        Parent content = loader.load();

        stage.setTitle("Task Manager");
        stage.setScene(new Scene(content));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
