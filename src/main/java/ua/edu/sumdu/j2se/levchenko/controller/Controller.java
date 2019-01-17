package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.stage.Stage;

public abstract class Controller {
    protected Stage window;

    public void setWindow(Stage window) {
        this.window = window;
    }

    abstract void showWindow();
}
