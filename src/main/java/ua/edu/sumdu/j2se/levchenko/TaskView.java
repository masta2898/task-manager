package ua.edu.sumdu.j2se.levchenko;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class TaskView {
    private SimpleStringProperty title;

    private SimpleStringProperty start = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty end = new SimpleStringProperty();

    private SimpleIntegerProperty interval;

    private SimpleStringProperty active;
    private SimpleStringProperty repeated;

    public TaskView(String title, Date time, boolean active) {
        this.title = new SimpleStringProperty(title);
        this.time = new SimpleStringProperty(time != null ? time.toString() : "");
        this.active = new SimpleStringProperty(active ? "Yes" : "No");
        this.repeated = new SimpleStringProperty("No");
    }

    public TaskView(String title, Date start, Date end, int interval, boolean active) {
        this.title = new SimpleStringProperty(title);
        this.start = new SimpleStringProperty(start != null ? start.toString() : "");
        this.end = new SimpleStringProperty(start != null ? end.toString() : "");
        this.interval = new SimpleIntegerProperty(interval);
        this.active = new SimpleStringProperty(active ? "Yes" : "No");
        this.repeated = new SimpleStringProperty("Yes");
    }

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }

    public void setEnd(String end) {
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
