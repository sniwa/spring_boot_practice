package com.example.introduction.entity;

import java.io.Serializable;
import java.util.Date;

public class TaskPerDayEntity implements Serializable {
    private Date date;
    private int completed;
    private int created;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }
}
