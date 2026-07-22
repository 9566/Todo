package com.tap.spring_mvc.service;

import com.tap.spring_mvc.model.Todo;
import com.tap.spring_mvc.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    // 3. Service receives the "todo" object from the Controller
    public Todo createTodo(Todo todo) {
        // 4. Service calls the Repository layer's method to save the data
        Todo savedTodoFromDb = todoRepository.save(todo);
        // 5. Service receives the saved object back and returns it to the Controller
        return savedTodoFromDb;
    }


    public Todo getTodoById(Long id){
        return todoRepository.findById(id).orElseThrow(()->new RuntimeException("Todo not found"));
    }

    public List<Todo> getTodoAll(){
        return todoRepository.findAll();
    }

    public Todo updateTodo(Todo todo){
        Todo updateTodoFromDb=todoRepository.save(todo);
        return updateTodoFromDb;
    }
    public void deleteTodoId(Long id){
         todoRepository.delete(getTodoById(id));
    }

    public void deleteTodo(Todo todo){
        todoRepository.delete(todo);
    }

    public Page<Todo> getAllPage(int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        return todoRepository.findAll(pageable);
    }

}
