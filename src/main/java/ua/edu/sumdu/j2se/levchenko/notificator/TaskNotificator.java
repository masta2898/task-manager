package ua.edu.sumdu.j2se.levchenko.notificator;

import ua.edu.sumdu.j2se.levchenko.controller.NotificationObserverController;
import ua.edu.sumdu.j2se.levchenko.tasks.TaskList;
import ua.edu.sumdu.j2se.levchenko.tasks.repository.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TaskNotificator extends Notificator {
    private final Repository repository;
    private final NotificationObserverController notificationObserver;

    public TaskNotificator(final Repository repository, NotificationObserverController notificationObserver) {
        this.repository = repository;
        this.notificationObserver = notificationObserver;
        setDaemon(true);
    }

    @Override
    public void run() {
        long delay = 1;
        long oneMinuteInMillis = 60 * 1000;

        while (true) {
            Calendar now = Calendar.getInstance();
            long nowInMillis = now.getTimeInMillis();
            Date afterTenMinutes = new Date(nowInMillis + (delay * oneMinuteInMillis));

            TaskList tasks = repository.getTasksByPeriod(now.getTime(), afterTenMinutes);
            notificationObserver.showNotification(tasks);

            try {
                TimeUnit.MINUTES.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
