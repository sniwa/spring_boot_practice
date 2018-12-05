package com.example.introduction.entity;

import java.io.Serializable;

public class TaskListEntity implements Serializable {
    private String taskFetchMode;


    public String getTaskFetchMode() {
        return taskFetchMode;
    }

    public void setTaskFetchMode(String taskFetchMode) {
        this.taskFetchMode = taskFetchMode;
    }
}
