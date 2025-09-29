package com.example.Todo.App.controller.admin;

import com.example.Todo.App.dto.TaskDTO;
import com.example.Todo.App.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers  (){
        return ResponseEntity.ok(adminService.getUsers());
   }

@PostMapping("/task")
    public  ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        TaskDTO createdTaskDto=adminService.createTask(taskDTO);
        if(createdTaskDto == null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDto);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks  (){
        return ResponseEntity.ok(adminService.getAllTasks());
    }
@DeleteMapping("/task/{id}")
    public  ResponseEntity<Void> deleteTask(@PathVariable String id){
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable String id){
        return  ResponseEntity.ok(adminService.getTaskById(id));
    }
    @PutMapping(  "/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody TaskDTO taskDTO){
        TaskDTO updateTask = adminService.updateTask(id,taskDTO);
        if(updateTask == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(updateTask);
    }
@GetMapping("/tasks/search/{title}")
    public  ResponseEntity<List<TaskDTO>> searchTask(@PathVariable String title){
        return ResponseEntity.ok(adminService.searchTaskByTitle(title));
    }
}
