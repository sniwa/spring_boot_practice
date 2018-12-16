package com.example.introduction.api.task.controller;

import com.example.introduction.api.task.service.TaskApiService;
import com.example.introduction.entity.APIResponse;
import com.example.introduction.entity.TaskPerDayEntity;
import com.example.introduction.entity.TaskPerWeekEntity;
import com.example.introduction.entity.TaskStatsEntity;
import com.example.introduction.form.TaskForm;
import com.example.introduction.gen.entity.Task;
import com.example.introduction.gen.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskApiController {
    private final TaskApiService taskApiService;

    private User getUserFromSession(HttpSession session) {
        Object loginUser = session.getAttribute("LOGIN_USER");
        if (loginUser instanceof  User) {
            return (User)loginUser;
        }

        return null;
    }

    @Autowired
    public TaskApiController(TaskApiService taskApiService) {
        this.taskApiService = taskApiService;
    }

    @RequestMapping(path= "/task/list")
    public List<Task> list(HttpSession session) {
        User user = getUserFromSession(session);
        return taskApiService.listTasks(user);
    }

    @RequestMapping(path= "/task/list/completed")
    public List<Task> completedList(HttpSession session) {
        User user = getUserFromSession(session);
        return taskApiService.listCompletedTasks(user);
    }

    @RequestMapping(path = "/task/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Task insertTask(@Validated @RequestBody TaskForm form, HttpSession session) {
        User user = getUserFromSession(session);

        Task task = new Task();
        task.setTitle(form.getTitle());
        task.setText(form.getText());

        if (!StringUtils.isEmpty(form.getPriority())) {
            String prior = form.getPriority();
            int priority = -1;
            switch (prior) {
                case "low":
                    priority = 1;
                    break;
                case "middle":
                    priority = 2;
                    break;
                case "high":
                    priority = 3;
                    break;
            }
            task.setPriority(priority);
        }

        int id = taskApiService.insertTask(task, user);
        return taskApiService.getTask(id, user);
    }

    @RequestMapping(path = "/task/{id}/delete", method = RequestMethod.POST)
    public APIResponse deleteTask(@PathVariable("id") int id, HttpSession session) {
        User user = getUserFromSession(session);

        APIResponse response = new APIResponse();
        boolean result = taskApiService.deleteTask(id, user);
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
    public APIResponse doneTask(@PathVariable("id") int id, HttpSession session) {
        User user = getUserFromSession(session);

        APIResponse response = new APIResponse();

        boolean result = taskApiService.doneTask(id, user);
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
    public APIResponse updateTask(@PathVariable("id") int id, @Validated @RequestBody TaskForm form, HttpSession session) {
        User user = getUserFromSession(session);
        APIResponse response = new APIResponse();

        Task task = taskApiService.getTask(id, user);

        task.setText(form.getText());
        task.setTitle(form.getTitle());

        Task result = taskApiService.updateTask(task, user);

        if (result == null) {
            response.setStatusCode(404);
            response.setMessage("not found");

            return response;
        }

        response.setMessage("success");
        response.setStatusCode(200);

        return response;
    }

    @RequestMapping(path = "/stats/total")
    public APIResponse getTotalCounts(HttpSession session) {
        User user = getUserFromSession(session);
        APIResponse response = new APIResponse();

        TaskStatsEntity stats = taskApiService.fetchCounts(user);
        response.setResponse(stats);
        response.setStatusCode(200);
        response.setMessage("completed");

        return response;
    }

    @RequestMapping(path = "/stats/week")
    public APIResponse getWeekStats(HttpSession session) {
        User user = getUserFromSession(session);
        APIResponse response = new APIResponse();

        List<TaskPerDayEntity> dates = taskApiService.fetchWeekProgress(new Date(), user);
        TaskPerWeekEntity entity = new TaskPerWeekEntity();
        entity.setDates(dates);

        response.setStatusCode(200);
        response.setMessage("completed");
        response.setResponse(entity);

        return response;
    }
}
