package com.example.introduction.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @RequestMapping(path = "/")
    public String dashboard(Model model) {
        model.addAttribute("currentDisplayIndex", 0);
        return "index";
    }

    @RequestMapping(path = "/tasks")
    public String tasks(Model model) {
        model.addAttribute("currentDisplayIndex", 1);
        return "tasks";
    }

    @RequestMapping(path = "/tasks", params = "kind=completed")
    public String completedTasks(Model model) {
        model.addAttribute("currentDisplayIndex", 2);
        return "tasks";
    }
}
