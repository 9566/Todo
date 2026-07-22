package com.tap.spring_mvc.repository;

import com.tap.spring_mvc.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

//CRUD
@Component
public interface TodoRepository extends JpaRepository<Todo,Long> {
}
