package ua.edu.sumdu.j2se.levchenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.levchenko.controller.MainController;

public class TaskManager extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new MainController());
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
