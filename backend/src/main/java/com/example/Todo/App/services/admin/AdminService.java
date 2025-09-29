package com.example.Todo.App.services.admin;

import com.example.Todo.App.dto.TaskDTO;
import com.example.Todo.App.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List <UserDTO> getUsers();
    TaskDTO createTask(TaskDTO taskDTO);
    List<TaskDTO> getAllTasks();
    void deleteTask(String id);
    TaskDTO getTaskById(String id);
    TaskDTO updateTask(String id,TaskDTO taskDTO);
    List <TaskDTO> searchTaskByTitle (String title);

}
