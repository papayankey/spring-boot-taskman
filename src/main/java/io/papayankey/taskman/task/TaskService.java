package io.papayankey.taskman.task;

import java.util.List;

interface TaskService {
    List<TaskResponse> getTasks();

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse getTask(int id);

    List<TaskResponse> getTasksByStatus(String taskStatus);

    TaskResponse deleteTask(int id);

    void updateTask(int id, TaskRequest taskRequest);
}
