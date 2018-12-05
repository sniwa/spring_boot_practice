package com.example.introduction.index.controller;

import com.example.introduction.entity.TaskListEntity;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(path = "/")
    public String dashboard(Model model) {
        model.addAttribute("currentDisplayIndex", 0);
        return "index";
    }

    @RequestMapping(path = "/tasks")
    public String tasks(Model model) {
        TaskListEntity entity = new TaskListEntity();
        entity.setTaskFetchMode("normal");

        Gson gson = new Gson();
        model.addAttribute("dataContext", gson.toJson(entity));
        model.addAttribute("currentDisplayIndex", 1);
        return "tasks";
    }

    @RequestMapping(path = "/tasks", params = "kind=completed")
    public String completedTasks(Model model) {
        TaskListEntity entity = new TaskListEntity();
        entity.setTaskFetchMode("completed");

        Gson gson = new Gson();

        model.addAttribute("dataContext", gson.toJson(entity));
        model.addAttribute("currentDisplayIndex", 2);
        return "tasks";
    }
}
