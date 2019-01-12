package ua.edu.sumdu.j2se.levchenko;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class TaskView {
    private SimpleStringProperty title;

    private SimpleObjectProperty<Date> start;
    private SimpleObjectProperty<Date> time;
    private SimpleObjectProperty<Date> end;

    private SimpleIntegerProperty interval;

    private SimpleStringProperty active;
    private SimpleStringProperty repeated;

    public TaskView(String title, Date time, String active) {
        this.title = new SimpleStringProperty(title);
        this.time = new SimpleObjectProperty<>(time);
        this.active = new SimpleStringProperty(active);
        this.repeated = new SimpleStringProperty("No");
    }

    public TaskView(String title, Date start, Date end, int interval, String active) {
        this.title = new SimpleStringProperty(title);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
        this.interval = new SimpleIntegerProperty(interval);
        this.active = new SimpleStringProperty(active);
        this.repeated = new SimpleStringProperty("Yes");
    }

    public Date getStart() {
        return start.get();
    }

    public SimpleObjectProperty<Date> startProperty() {
        return start;
    }

    public void setStart(Date start) {
        this.start.set(start);
    }

    public Date getTime() {
        return time.get();
    }

    public SimpleObjectProperty<Date> timeProperty() {
        return time;
    }

    public void setTime(Date time) {
        this.time.set(time);
    }

    public Date getEnd() {
        return end.get();
    }

    public SimpleObjectProperty<Date> endProperty() {
        return end;
    }

    public void setEnd(Date end) {
        this.end.set(end);
    }

    public int getInterval() {
        return interval.get();
    }

    public SimpleIntegerProperty intervalProperty() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval.set(interval);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getActive() {
        return active.get();
    }

    public SimpleStringProperty activeProperty() {
        return active;
    }

    public void setActive(String active) {
        this.active.set(active);
    }

    public String getRepeated() {
        return repeated.get();
    }

    public SimpleStringProperty repeatedProperty() {
        return repeated;
    }

    public void setRepeated(String repeated) {
        this.repeated.set(repeated);
    }
}
