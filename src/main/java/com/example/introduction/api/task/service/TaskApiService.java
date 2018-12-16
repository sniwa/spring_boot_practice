package com.example.introduction.api.task.service;

import com.example.introduction.entity.TaskPerDayEntity;
import com.example.introduction.entity.TaskStatsEntity;
import com.example.introduction.gen.entity.*;
import com.example.introduction.gen.mapper.TaskMapper;
import com.example.introduction.gen.mapper.TaskPermissionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskApiService {

    private final TaskMapper taskMapper;

    private final TaskPermissionsMapper taskPermissionsMapper;

    @Autowired
    public TaskApiService(TaskMapper taskMapper, TaskPermissionsMapper taskPermissionsMapper) {
        this.taskMapper = taskMapper;
        this.taskPermissionsMapper = taskPermissionsMapper;
    }

    private TaskPermissionsExample getTaskPermissionsExample(int taskId, User user) {
        TaskPermissionsExample permissionsExample = new TaskPermissionsExample();
        permissionsExample.createCriteria().andTaskIdEqualTo(taskId).andUserIdEqualTo(user.getId());
        return permissionsExample;
    }

    private List<Integer> getOwnedTaskIds(User user) {
        TaskPermissionsExample permissionsExample = new TaskPermissionsExample();
        permissionsExample.createCriteria().andUserIdEqualTo(user.getId());
        List<TaskPermissions> permissions = taskPermissionsMapper.selectByExample(permissionsExample);
        return permissions.stream().map(TaskPermissions::getTaskId).collect(Collectors.toList());
    }

    public Task getTask(int taskId, User user) {
        TaskPermissionsExample taskPermissionsExample = getTaskPermissionsExample(taskId, user);
        List<TaskPermissions> permissions = taskPermissionsMapper.selectByExample(taskPermissionsExample);

        if (permissions.size() == 0) {
            return null;
        }

        return taskMapper.selectByPrimaryKey(taskId);
    }

    @Transactional
    public int insertTask(Task task, User user) {
        int result = taskMapper.insertSelective(task);
        TaskPermissions permissions = new TaskPermissions();
        permissions.setTaskId(task.getId());
        permissions.setUserId(user.getId());
        taskPermissionsMapper.insert(permissions);

        return task.getId();
    }

    @Transactional
    public boolean deleteTask(int taskId, User user) {
        TaskPermissionsExample permissionsExample = getTaskPermissionsExample(taskId, user);
        int result = taskPermissionsMapper.deleteByExample(permissionsExample);
        if (result == 0) {
            return false;
        }

        Task task = new Task();
        task.setId(taskId);
        task.setDeleted(1);
        int deleteResult = taskMapper.updateByPrimaryKeySelective(task);
        return deleteResult > 0;
    }

    public Task updateTask(Task task, User user) {
        TaskPermissionsExample permissionsExample = getTaskPermissionsExample(task.getId(), user);
        List<TaskPermissions> permissions = taskPermissionsMapper.selectByExample(permissionsExample);
        if (permissions.size() == 0) {
            return null;
        }

        int result = taskMapper.updateByPrimaryKeySelective(task);
        if (result == 0) {
            return null;
        }
        return task;
    }

    public List<Task> listTasks(User user) {
        List<Integer> ownedTaskIds = getOwnedTaskIds(user);

        TaskExample example = new TaskExample();
        example.createCriteria()
                .andCompletedEqualTo(0)
                .andDeletedEqualTo(0)
                .andIdIn(ownedTaskIds);

        return taskMapper.selectByExampleWithBLOBs(example);
    }

    public List<Task> listCompletedTasks(User user) {
        List<Integer> ownedTaskIds = getOwnedTaskIds(user);

        TaskExample example = new TaskExample();
        example.createCriteria()
                .andCompletedEqualTo(1)
                .andDeletedEqualTo(0)
                .andIdIn(ownedTaskIds);
        example.setOrderByClause("completed_date ASC");
        return taskMapper.selectByExampleWithBLOBs(example);
    }

    public boolean doneTask(int id, User user) {
        Task targetTask = getTask(id, user);

        if (targetTask == null) {
            return false;
        }

        targetTask.setCompleted(1);
        targetTask.setCompletedDate(new Date());

        Task result = updateTask(targetTask, user);
        return result != null;
    }

    public TaskStatsEntity fetchCounts(User user) {
        List<Integer> ownedTaskIds = getOwnedTaskIds(user);

        TaskStatsEntity entity = new TaskStatsEntity();

        TaskExample completedExample = new TaskExample();
        completedExample.createCriteria().andCompletedEqualTo(1).andIdIn(ownedTaskIds);
        TaskExample deletedExample = new TaskExample();
        deletedExample.createCriteria().andDeletedEqualTo(1).andIdIn(ownedTaskIds);
        TaskExample progressExample = new TaskExample();
        progressExample.createCriteria()
                .andDeletedEqualTo(0)
                .andCompletedEqualTo(0)
                .andIdIn(ownedTaskIds);

        long completed = taskMapper.countByExample(completedExample);
        long deleted = taskMapper.countByExample(deletedExample);
        long progress = taskMapper.countByExample(progressExample);

        entity.setCompleted((int) completed);
        entity.setDeleted((int) deleted);
        entity.setProgress((int) progress);

        return entity;
    }

    public List<TaskPerDayEntity> fetchWeekProgress(Date baseDate, User user) {
        List<Integer> ownedTaskIds = getOwnedTaskIds(user);

        List<TaskPerDayEntity> taskPerDayEntities = new ArrayList<>();
        LocalDateTime time = LocalDateTime.ofInstant(baseDate.toInstant(), ZoneId.systemDefault());

        for (int i = 0; i < 7; i++) {
            LocalDateTime startDateTime = time.minusDays(i).truncatedTo(ChronoUnit.DAYS);
            Date startDate = Date.from(startDateTime.toInstant(OffsetDateTime.now().getOffset()));
            LocalDateTime endDateTime = startDateTime.plusDays(1).minusNanos(1).truncatedTo(ChronoUnit.NANOS);
            Date endDate = Date.from(endDateTime.toInstant(OffsetDateTime.now().getOffset()));

            TaskExample completedExample = new TaskExample();
            completedExample.createCriteria().andCompletedDateBetween(startDate, endDate).andIdIn(ownedTaskIds);
            TaskExample createdExample = new TaskExample();
            createdExample.createCriteria().andCreatedBetween(startDate, endDate).andIdIn(ownedTaskIds);

            long completedCount = taskMapper.countByExample(completedExample);
            long createdCount = taskMapper.countByExample(createdExample);

            TaskPerDayEntity dayEntity = new TaskPerDayEntity();
            dayEntity.setDate(startDate);
            dayEntity.setCompleted((int) completedCount);
            dayEntity.setCreated((int) createdCount);
            taskPerDayEntities.add(dayEntity);
        }

        return taskPerDayEntities;
    }
}


