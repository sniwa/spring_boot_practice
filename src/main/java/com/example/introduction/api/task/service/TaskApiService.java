package com.example.introduction.api.task.service;

import com.example.introduction.gen.entity.Task;
import com.example.introduction.gen.entity.TaskExample;
import com.example.introduction.gen.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        Task task = new Task();
        task.setId(id);
        task.setDeleted(1);
        int result = taskMapper.updateByPrimaryKeySelective(task);
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
        TaskExample example = new TaskExample();
        example.createCriteria()
                .andCompletedEqualTo(0)
                .andDeletedEqualTo(0);
        return taskMapper.selectByExampleWithBLOBs(example);
    }

    public List<Task> listCompletedTasks() {
        TaskExample example = new TaskExample();
        example.createCriteria()
                .andCompletedEqualTo(1)
                .andDeletedEqualTo(0);
        return taskMapper.selectByExampleWithBLOBs(example);
    }

    public boolean doneTask(int id) {
        Task targetTask = getTask(id);

        if (targetTask == null) {
            return false;
        }

        targetTask.setCompleted(1);
        targetTask.setCompletedDate(new Date());

        Task result = updateTask(targetTask);
        if (result == null) {
            return false;
        }

        return true;
    }
}
