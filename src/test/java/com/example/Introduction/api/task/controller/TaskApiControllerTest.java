package com.example.Introduction.api.task.controller;

import com.example.introduction.gen.entity.Task;
import com.example.introduction.gen.entity.TaskExample;
import com.example.introduction.gen.mapper.TaskMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaskApiControllerTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void index() {
    }

    @Test
    public void list() {
        Assert.assertNotNull(taskMapper);
        Task task = new Task();
        task.setText("text text");
        task.setTitle("title");

        int result = taskMapper.insertSelective(task);


        Assert.assertEquals(1, result);

        TaskExample example = new TaskExample();
        example.createCriteria();
        List<Task> foundTasks = taskMapper.selectByExampleWithBLOBs(example);
        Assert.assertEquals(1, foundTasks.size());

        Task found = foundTasks.get(0);
        Assert.assertEquals("text text", found.getText());
        Assert.assertEquals(0, found.getDeleted().intValue());
    }
}