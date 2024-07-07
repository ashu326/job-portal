package main.controllers;

import main.entities.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // define endpoints
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        User u = this.userRepository.getUserById(userId);
        if(u == null) {
            throw new UserNotFoundException("User not found for id " + userId);
        }
        System.out.println("Fetched user " + u);
        return this.userRepository.getUserById(userId);
    }

    // add exception handler
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exc) {
        UserErrorResponse error = new UserErrorResponse();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
