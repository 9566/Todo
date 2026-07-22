package com.tap.spring_mvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor  // <-- Add this
@AllArgsConstructor // <-- Add this
@Table(name = "UserTable")   // in sql it will user name as useratble in java use user.
public class User {

    @GeneratedValue
    @Id
    Long id;
    @Email
    String email;
    String password;

}
