package com.example.Todo.App.controller.employee;

import com.example.Todo.App.dto.TaskDTO;
import com.example.Todo.App.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController  {
    private final EmployeeService employeeService;
@GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTaskByUserId(){
        return ResponseEntity.ok(employeeService.getTaskByUserId());
    }
    @GetMapping("/task/{id}/{status}")
    public  ResponseEntity<TaskDTO>  updateTask(@PathVariable String id,@PathVariable String status ){
    TaskDTO updatedTaskDTO = employeeService.updateTask(id,status);
    if(updatedTaskDTO == null)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    return ResponseEntity.ok(updatedTaskDTO);
    }
}

























































