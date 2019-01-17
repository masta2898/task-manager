package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.scene.media.MediaPlayer;
import ua.edu.sumdu.j2se.levchenko.tasks.TaskList;

public abstract class NotificationObserverController extends Controller {
    protected MediaPlayer notificationSoundPlayer;

    public void setNotificationSound(MediaPlayer notificationSoundPlayer) {
        this.notificationSoundPlayer = notificationSoundPlayer;
    }

    abstract public void showNotification(TaskList tasks);
}
