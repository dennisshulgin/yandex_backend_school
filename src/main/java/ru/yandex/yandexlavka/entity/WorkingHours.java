package ru.yandex.yandexlavka.entity;

import java.time.LocalTime;

public class WorkingHours {
    private int id;

    private LocalTime start;

    private LocalTime end;

    public WorkingHours() {
    }

    public WorkingHours(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
