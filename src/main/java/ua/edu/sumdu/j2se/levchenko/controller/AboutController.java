package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.fxml.FXML;

public class AboutController extends Controller {
    @Override
    public void showWindow() {
        window.showAndWait();
    }

    @FXML
    void close() {
        window.close();
    }
}
