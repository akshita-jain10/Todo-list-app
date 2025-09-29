package com.example.Todo.App.services.admin;

import com.example.Todo.App.dto.TaskDTO;
import com.example.Todo.App.dto.UserDTO;
import com.example.Todo.App.entities.Task;
import com.example.Todo.App.entities.User;
import com.example.Todo.App.enums.TaskStatus;
import com.example.Todo.App.enums.UserRole;
import com.example.Todo.App.repositories.TaskRepository;
import com.example.Todo.App.repositories.UserRepository;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService  {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<UserDTO> getUsers() {
        return userRepository
                .findAll().stream().filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User ::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());
        if(optionalUser.isPresent()){
            Task task = new Task();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setPriority(taskDTO.getPriority());
            task.setDueDate(taskDTO.getDueDate());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
        return taskRepository.save(task).getTaskDTO();
        }
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream().sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(String id) {
    taskRepository.deleteById(id);
    }

    @Override
    public TaskDTO getTaskById(String id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDTO).orElse(null);
    }

    @Override
    public TaskDTO updateTask(String id, TaskDTO taskDTO) {
         Optional<Task> optionalTask = taskRepository.findById(id);
         Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());
        if(optionalTask.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setPriority(taskDTO.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDTO.getTaskStatus())));
            existingTask.setUser(optionalUser.get());
            return taskRepository.save(existingTask).getTaskDTO();
        }
        return  null;
    }

    @Override
    public List<TaskDTO> searchTaskByTitle(String title) {
        return taskRepository.findAllByTitleContaining(title);
    }

    private TaskStatus mapStringToTaskStatus(String status){
       return switch (status){
            case "PENDING" -> TaskStatus.PENDING;
           case "INPROGRESS" -> TaskStatus.INPROGRESS;
           case "COMPLETED" -> TaskStatus.COMPLETED;
           case "DEFERRED" -> TaskStatus.DEFERRED;
           default->TaskStatus.CANCELLED;
       };
        }
}

