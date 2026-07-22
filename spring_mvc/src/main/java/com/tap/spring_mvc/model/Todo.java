package com.tap.spring_mvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data   //it will write getters and setters automatically
public class Todo {
    @Id
    @GeneratedValue
    Long id;
    String title;
    String description;
    String isCompleted;

}
