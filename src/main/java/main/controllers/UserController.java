package main.controllers;

import main.entities.LoginDto;
import main.entities.RegisterUserDto;
import main.entities.User;
import main.exceptions.UserErrorResponse;
import main.exceptions.UserNotFoundException;
import main.services.AuthenticationService;
import main.services.UserService;
import main.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private AuthenticationService authenticationService;

    @Autowired
    UserController(
            UserService userService,
            AuthenticationService authenticationService
    ) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }
    @PostMapping("/users")
    public User addUser(@RequestBody User userDetails) {
        return userService.addNewUser(userDetails);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User userDetails) {
        return userService.updateUser(userDetails);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId) {
        userService.deleteUserById(userId);
    }

    @PostMapping("/auth/login")
    public Map<String, Object> authenticateUser(@RequestBody LoginDto authDetails) {
        User user = authenticationService.authenticate(authDetails);
        String token = JwtUtil.generateToken(null, user.getEmail());

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);

        return res;
    }

    @PostMapping("/auth/signup")
    public User registerUser(@RequestBody RegisterUserDto userDetails) {
        return authenticationService.register(userDetails);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException exc) {
        UserErrorResponse error = new UserErrorResponse();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
