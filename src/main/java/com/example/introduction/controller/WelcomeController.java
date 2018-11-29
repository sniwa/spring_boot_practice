package com.example.introduction.controller;

import com.example.introduction.gen.entity.Task;
import com.example.introduction.gen.entity.TaskExample;
import com.example.introduction.gen.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {

    @Autowired
    TaskMapper taskMapper;

    @RequestMapping(path = "/", method=RequestMethod.GET)
    public String index() {
        return "Welcome To My World 35";
    }

    @RequestMapping(path= "/list")
    public List<Task> list() {
        Task task = new Task();
        task.setTitle("Hello");
        task.setText("Dreaming Mermaid");

        int result = taskMapper.insertSelective(task);

        return taskMapper.selectByExampleWithBLOBs(new TaskExample());
    }
}
