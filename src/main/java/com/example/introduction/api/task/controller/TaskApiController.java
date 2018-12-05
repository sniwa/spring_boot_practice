package com.example.introduction.api.task.controller;

import com.example.introduction.api.task.service.TaskApiService;
import com.example.introduction.entity.APIResponse;
import com.example.introduction.form.TaskForm;
import com.example.introduction.gen.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskApiController {
    private final TaskApiService taskApiService;

    @Autowired
    public TaskApiController(TaskApiService taskApiService) {
        this.taskApiService = taskApiService;
    }

    @RequestMapping(path= "/task/list")
    public List<Task> list() {
        return taskApiService.listTasks();
    }

    @RequestMapping(path= "/task/list/completed")
    public List<Task> completedList() {
        return taskApiService.listCompletedTasks();
    }



    @RequestMapping(path = "/task/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Task insertTask(@Validated @RequestBody TaskForm form) {
        Task task = new Task();
        task.setTitle(form.getTitle());
        task.setText(form.getText());

        int id = taskApiService.insertTask(task);
        return taskApiService.getTask(id);
    }

    @RequestMapping(path = "/task/{id}/delete", method = RequestMethod.POST)
    public APIResponse deleteTask(@PathVariable("id") int id) {
        APIResponse response = new APIResponse();

        boolean result = taskApiService.deleteTask(id);
        if (!result) {
            response.setStatusCode(404);
            response.setMessage("not found");

            return response;
        }

        response.setMessage("success");
        response.setStatusCode(200);

        return response;
    }

    @RequestMapping(path = "/task/{id}/done", method = RequestMethod.POST)
    public APIResponse doneTask(@PathVariable("id") int id) {
        APIResponse response = new APIResponse();

        boolean result = taskApiService.doneTask(id);
        if (!result) {
            response.setStatusCode(404);
            response.setMessage("not found");

            return response;
        }

        response.setMessage("success");
        response.setStatusCode(200);

        return response;
    }

    @RequestMapping(path = "/task/{id}/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse updateTask(@PathVariable("id") int id, @Validated @RequestBody TaskForm form) {
        APIResponse response = new APIResponse();

        Task task = taskApiService.getTask(id);

        task.setText(form.getText());
        task.setTitle(form.getTitle());

        Task result = taskApiService.updateTask(task);

        if (result == null) {
            response.setStatusCode(404);
            response.setMessage("not found");

            return response;
        }

        response.setMessage("success");
        response.setStatusCode(200);

        return response;
    }
}
