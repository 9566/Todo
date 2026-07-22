package com.tap.spring_mvc.service;

import com.tap.spring_mvc.model.Todo;
import com.tap.spring_mvc.model.User;
import com.tap.spring_mvc.repository.TodoRepository;
import com.tap.spring_mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        User savedUserFromDb = userRepository.save(user);
        return savedUserFromDb;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Todo not found"));
    }

}
