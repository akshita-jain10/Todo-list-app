package com.example.Todo.App.services.employee;

import com.example.Todo.App.dto.TaskDTO;
import com.example.Todo.App.entities.Task;
import com.example.Todo.App.entities.User;
import com.example.Todo.App.enums.TaskStatus;
import com.example.Todo.App.repositories.TaskRepository;
import com.example.Todo.App.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final TaskRepository taskRepository;
    private  final JwtUtil jwtUtil;
    @Override
    public List<TaskDTO> getTaskByUserId() {

        User user =jwtUtil.getLoggedInUser();
        if(user != null){
            return  taskRepository.findALlByUserId (user.getId()).stream().sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public TaskDTO updateTask(String id, String status) {
        Optional<Task> optionalTask=taskRepository.findById(id);
        if(optionalTask.isPresent()){
        Task existingTask = optionalTask.get();
                existingTask.setTaskStatus(mapStringToTaskStatus(status));
                return taskRepository.save(existingTask).getTaskDTO();
        }
        throw new NoSuchElementException("Task Not Found");
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
