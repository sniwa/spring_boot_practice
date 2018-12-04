package com.example.introduction.api.task.service;

import com.example.introduction.gen.entity.Task;
import com.example.introduction.gen.entity.TaskExample;
import com.example.introduction.gen.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskApiService {

    private final TaskMapper taskMapper;

    @Autowired
    public TaskApiService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public Task getTask(int id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    public int insertTask(Task task) {
        int result = taskMapper.insertSelective(task);
        return task.getId();
    }

    public boolean deleteTask(int id) {
        int result = taskMapper.deleteByPrimaryKey(id);
        return result > 0;
    }

    public Task updateTask(Task task) {
        int result = taskMapper.updateByPrimaryKeySelective(task);
        if (result == 0) {
            return null;
        }
        return task;
    }

    public List<Task> listTasks() {
        return taskMapper.selectByExampleWithBLOBs(new TaskExample());
    }
}
