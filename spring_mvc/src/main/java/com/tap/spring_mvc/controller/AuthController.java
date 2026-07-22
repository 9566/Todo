package com.tap.spring_mvc.controller;

import com.tap.spring_mvc.model.User;
import com.tap.spring_mvc.repository.UserRepository;
import com.tap.spring_mvc.service.UserService;
import com.tap.spring_mvc.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor  //insted of autowired we can use this;
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String> body){
        String email=body.get("email");
        String password=passwordEncoder.encode(body.get("password")) ;
        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }
        userService.createUser(User.builder().email(email).password(password).build());   // usally we will give the object but here we are not giving .with out giving object also we can use using builder.
        return new ResponseEntity<>( "Successfully Registered", HttpStatus. CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity <? > loginUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not Registered", HttpStatus.UNAUTHORIZED);
        }
            User user = userOptional.get();
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new ResponseEntity<>("Invalid User", HttpStatus.UNAUTHORIZED);
            }

            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(Map.of("token", token));

        }

}
