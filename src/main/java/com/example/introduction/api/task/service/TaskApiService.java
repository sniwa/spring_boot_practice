package com.example.introduction.api.task.service;

import com.example.introduction.entity.TaskPerDayEntity;
import com.example.introduction.entity.TaskStatsEntity;
import com.example.introduction.gen.entity.Task;
import com.example.introduction.gen.entity.TaskExample;
import com.example.introduction.gen.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        example.setOrderByClause("completed_date ASC");
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

    public TaskStatsEntity fetchCounts() {
        TaskStatsEntity entity = new TaskStatsEntity();

        TaskExample completedExample = new TaskExample();
        completedExample.createCriteria().andCompletedEqualTo(1);
        TaskExample deletedExample = new TaskExample();
        deletedExample.createCriteria().andDeletedEqualTo(1);
        TaskExample progressExample = new TaskExample();
        progressExample.createCriteria()
                .andDeletedEqualTo(0)
                .andCompletedEqualTo(0);

        long completed = taskMapper.countByExample(completedExample);
        long deleted = taskMapper.countByExample(deletedExample);
        long progress = taskMapper.countByExample(progressExample);

        entity.setCompleted((int) completed);
        entity.setDeleted((int)deleted);
        entity.setProgress((int)progress);

        return entity;
    }

    public List<TaskPerDayEntity> fetchWeekProgress(Date baseDate) {
        List<TaskPerDayEntity> taskPerDayEntities = new ArrayList<>();
        LocalDateTime time = LocalDateTime.ofInstant(baseDate.toInstant(), ZoneId.systemDefault());
        for (int i = 0; i < 7; i++) {
            LocalDateTime startDateTime = time.minusDays(i).truncatedTo(ChronoUnit.DAYS);
            Date startDate = Date.from(startDateTime.toInstant(OffsetDateTime.now().getOffset()));
            LocalDateTime endDateTime = startDateTime.plusDays(1).minusNanos(1).truncatedTo(ChronoUnit.NANOS);
            Date endDate = Date.from(endDateTime.toInstant(OffsetDateTime.now().getOffset()));

            TaskExample completedExample = new TaskExample();
            completedExample.createCriteria().andCompletedDateBetween(startDate, endDate);
            TaskExample createdExample = new TaskExample();
            createdExample.createCriteria().andCreatedBetween(startDate, endDate);
            long completedCount = taskMapper.countByExample(completedExample);
            long createdCount = taskMapper.countByExample(createdExample);

            TaskPerDayEntity dayEntity = new TaskPerDayEntity();
            dayEntity.setDate(startDate);
            dayEntity.setCompleted((int)completedCount);
            dayEntity.setCreated((int)createdCount);
            taskPerDayEntities.add(dayEntity);
        }
        return taskPerDayEntities;
    }
}
