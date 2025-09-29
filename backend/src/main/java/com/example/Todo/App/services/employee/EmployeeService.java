package com.example.Todo.App.services.employee;

import com.example.Todo.App.dto.TaskDTO;

import java.util.List;

public interface EmployeeService {
    List<TaskDTO> getTaskByUserId();
    TaskDTO updateTask(String id,String status);
}
