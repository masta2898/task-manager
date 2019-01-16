package ua.edu.sumdu.j2se.levchenko.controller;

import javafx.scene.control.DatePicker;
import ua.edu.sumdu.j2se.levchenko.TaskView;
import ua.edu.sumdu.j2se.levchenko.tasks.Task;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

class ControllerHelper {
    static Date getDate(DatePicker view) {
        LocalDate localDate = view.getValue();
        if (localDate != null) {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            return Date.from(instant);
        }
        return null;
    }

    static LocalDate getLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    static Task taskViewToModel(TaskView view) {
        Task task;
        if (view.getRepeated().equals("Yes")) {
            task = new Task(view.getTitle(), view.getStart(), view.getEnd(), view.getInterval());
        } else {
            task = new Task(view.getTitle(), view.getTime());
        }
        task.setActive(view.getActive().equals("Yes"));
        return task;
    }

    static TaskView taskModelToView(Task task) {
        TaskView view;
        if (task.isRepeated()) {
            view = new TaskView(task.getTitle(), task.getStartTime(), task.getEndTime(),
                    task.getRepeatInterval(), task.isActive() ? "Yes" : "No");
        } else {
            view = new TaskView(task.getTitle(), task.getTime(), task.isActive() ? "Yes" : "No");
        }
        return view;
    }
}
