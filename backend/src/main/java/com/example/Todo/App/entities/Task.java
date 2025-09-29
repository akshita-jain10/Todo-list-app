package com.example.Todo.App.entities;

import com.example.Todo.App.dto.TaskDTO;
import com.example.Todo.App.enums.TaskStatus;
import com.example.Todo.App.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "tasks")
public class Task  {
    @Id
    private String id;
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;
    @DBRef(lazy = true)
    @JsonIgnore
    private User user;

    // Additional constructors if needed
    public Task() {}
public TaskDTO getTaskDTO(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(id);
        taskDTO.setTitle(title);
        taskDTO.setDescription(description);
        taskDTO.setPriority(priority);
        taskDTO.setDueDate(dueDate);
        taskDTO.setEmployeeId(user.getId());
        taskDTO.setEmployeeName(user.getName());
        taskDTO.setTaskStatus(taskStatus);
        return taskDTO;
    }
}