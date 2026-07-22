package com.tap.spring_mvc.controller;

import com.tap.spring_mvc.service.TodoService;
import com.tap.spring_mvc.model.Todo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    // 1. Controller receives the raw JSON and converts it into a "todo" object
    //@PostMapping("/create")
    @RequestMapping("/create")
    ResponseEntity<Todo> createUser(@RequestBody Todo todo) {
            // 2. Controller calls the Service layer's method and passes the "todo" object
            Todo savedTodo = todoService.createTodo(todo);
            // 6. Controller wraps the final returned object and sends it to the user
            return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    //@GetMapping("/{id}")

    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200" ,description = "WOw")
    })

    @RequestMapping("/{id}")
    ResponseEntity<Todo> getTodoId(@PathVariable long id){

        try{
            // 2. Controller calls the Service layer's method and passes thetodo object
            Todo getTodo = todoService.getTodoById(id);
            // 6. Controller wraps the final returned object and sends it to the user
            return new ResponseEntity<>(getTodo, HttpStatus.CREATED);
        }
        catch (RuntimeException exception){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping
    ResponseEntity<List<Todo>> getTodo(){
        return new ResponseEntity<List<Todo>>(todoService.getTodoAll(),HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<Todo> updateTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteTodoId(@PathVariable Long id){
        todoService.deleteTodoId(id);
    }

    @DeleteMapping("/delete")
    void deleteTodo(Todo todo){
         todoService.deleteTodo(todo);
    }

    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getTodosPaged(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(todoService.getAllPage(page, size), HttpStatus.OK);
    }
}
