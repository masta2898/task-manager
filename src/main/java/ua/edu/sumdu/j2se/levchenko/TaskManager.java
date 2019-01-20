package ua.edu.sumdu.j2se.levchenko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import ua.edu.sumdu.j2se.levchenko.controller.*;
import ua.edu.sumdu.j2se.levchenko.notificator.Notificator;
import ua.edu.sumdu.j2se.levchenko.notificator.TaskNotificator;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.TaskRepository;

import java.io.IOException;

public class TaskManager extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) throws Exception {
        Repository repository = new TaskRepository();

        MainController mainController = new MainControllerBuilder()
                .setTaskRepository(repository)
                .setAboutController(getController("/views/about.fxml", "About", new AboutController()))
                .setEditTaskController(getController("/views/task.fxml", "Edit Task", new EditTaskController()))
                .setTaskDetailsController(getController("/views/details.fxml", "Details", new TaskDetailsController()))
                .setTasksPeriodController(getController("/views/period.fxml", "Tasks by period", new TasksPeriodController()))
                .createMainController();

        FXMLLoader loader = new FXMLLoader();
        loader.setController(mainController);
        loader.setLocation(getClass().getResource("/views/main.fxml"));
        Parent content = loader.load();

        mainWindow.setTitle("Task Manager");
        mainWindow.setResizable(true);
        mainWindow.setScene(new Scene(content));
        mainWindow.getIcons().add(new Image(getClass().getResourceAsStream("/icons/cat.png")));

        Media notificationSound = new Media(getClass().getResource("/sounds/notification.mp3").toExternalForm());
        MediaPlayer notificationSoundPlayer = new MediaPlayer(notificationSound);
        mainController.setNotificationSound(notificationSoundPlayer);

        Notificator notificator = new TaskNotificator(repository, mainController);
        notificator.start();

        mainController.setWindow(mainWindow);
        mainController.showWindow();
    }

    private <C extends Controller> C getController(String filename, String title, C controller) throws IOException {
        Stage window = new Stage();
        controller.setWindow(window);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(controller);
        Parent parent = loader.load();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setScene(new Scene(parent));
        return controller;
    }

}
