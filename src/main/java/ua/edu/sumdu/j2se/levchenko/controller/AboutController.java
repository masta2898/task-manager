package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutController implements Controller {
    private Stage aboutWindow;

    @Override
    public void setWindow(Stage window) {
        this.aboutWindow = window;
    }

    @Override
    public void showWindow() {
        aboutWindow.showAndWait();
    }

    @FXML
    void close() {
        aboutWindow.close();
    }
}
