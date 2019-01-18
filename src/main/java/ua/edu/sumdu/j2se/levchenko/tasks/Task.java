package ua.edu.sumdu.j2se.levchenko.tasks;

import java.util.Date;

public class Task implements Cloneable {
    private final int milliseconds = 1000;
    private Date start;
    private Date time;
    private Date end;
    private int interval;
    private String title;
    private boolean active;
    private boolean repeated;

    public Task(String title, Date time) {
        this.time = time;
        this.title = title;
        this.active = false;
        this.repeated = false;
    }

    public Task(String title, Date start, Date end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = false;
        this.repeated = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTime() {
        if (repeated) {
            return start;
        } else {
            return time;
        }
    }

    public void setTime(Date time) {
        this.time = time;
        repeated = false;
    }

    public Date getStartTime() {
        if (repeated) {
            return start;
        } else {
            return time;
        }
    }

    public Date getEndTime() {
        if (repeated) {
            return end;
        } else {
            return time;
        }
    }

    public int getRepeatInterval() {
        if (repeated) {
            return interval;
        } else {
            return 0;
        }
    }

    public void setTime(Date start, Date end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeated = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        if (isRepeated() != task.isRepeated()) return false;
        if (isRepeated() && task.isRepeated()) {
            if (active != task.active) return false;
            if (!start.equals(task.getStartTime())) return false;
            if (!end.equals(task.getEndTime())) return false;
            if (interval != task.interval) return false;
        } else {
            if (active != task.active) return false;
            if (!time.equals(task.getTime())) return false;
        }
        return title.equals(task.getTitle());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = title.hashCode();
        if (isRepeated()) {
            result = prime * result + start.hashCode();
            result = prime * result + end.hashCode();
            result = prime * result + interval;
            result = prime * result + (active ? 1 : 0);
        } else {
            result = prime * result + time.hashCode();
            result = prime * result + (active ? 1 : 0);
        }
        return result;
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }

    @Override
    public String toString() {
        return "This is Task {" +
                "start = " + start +
                ", time = " + time +
                ", end = " + end +
                ", interval = " + interval +
                ", title = '" + title + '\'' +
                ", active = " + active +
                ", repeated = " + repeated +
                '}';
    }

    public boolean isRepeated() {
        return repeated;
    }

    public Date nextTimeAfter(Date current) {
        Date result = null;
        if (isActive()) {
            if (isRepeated()) {
                if (current.compareTo(start) < 0) {
                    result = start;
                } else {
                    if (current.before(end)) {
                        Date point = start;
                        long repeatNumber = ((end.getTime() - start.getTime())) / interval * milliseconds;
                        for (long i = 0; i <= repeatNumber; i++) {
                            Date next = new Date(point.getTime() + interval * milliseconds);
                            if (current.compareTo(point) >= 0
                                    && current.compareTo(next) < 0
                                    && next.compareTo(end) <= 0) {
                                result = next;
                                break;
                            }
                            point = next;
                        }
                    }
                }
            } else {
                if (current.compareTo(time) < 0) {
                    result = time;
                }
            }
        }
        return result;
    }
}
