package com.example.Todo.App.dto;

import com.example.Todo.App.enums.TaskStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;
    private String employeeId;
    private  String employeeName;
}
