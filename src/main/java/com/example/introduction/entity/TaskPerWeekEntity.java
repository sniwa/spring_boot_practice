package com.example.introduction.entity;

import java.io.Serializable;
import java.util.List;

public class TaskPerWeekEntity implements Serializable {
    private List<TaskPerDayEntity> dates;

    public List<TaskPerDayEntity> getDates() {
        return dates;
    }

    public void setDates(List<TaskPerDayEntity> dates) {
        this.dates = dates;
    }
}
