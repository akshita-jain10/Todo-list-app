package com.example.Todo.App.repositories;

import com.example.Todo.App.dto.TaskDTO;
import com.example.Todo.App.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository <Task,String>{
    List<TaskDTO> findAllByTitleContaining(String title);

    List<Task> findALlByUserId(String id);
    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
