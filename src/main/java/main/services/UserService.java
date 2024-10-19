package main.services;

import jakarta.transaction.Transactional;
import main.exceptions.UserNotFoundException;
import main.entities.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(User userDetails) {
        return userRepository.save(userDetails);
    }

    public User getUserById(int userId) {
        Optional<User> userFromDb = this.userRepository.findById(userId);
        if(userFromDb.isEmpty()) {
            throw new UserNotFoundException("User not found for id " + userId);
        }
        return userFromDb.get();
    }

    @Transactional
    public User updateUser(User userDetails) {
        return userRepository.save(userDetails);
    }

    @Transactional
    public void deleteUserById(int userId) {
        this.userRepository.deleteById(userId);
    }
}
