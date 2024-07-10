package main.services;

import main.entities.LoginDto;
import main.entities.RegisterUserDto;
import main.entities.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterUserDto userDetails) {
        User user = new User();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginDto authDetails) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDetails.getEmail(),
                        authDetails.getPassword()
                )
        );
        return userRepository.findByEmail(authDetails.getEmail());
    }
}
