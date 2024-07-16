package main.services;

import jakarta.transaction.Transactional;
import main.entities.LoginDto;
import main.entities.RegisterUserDto;
import main.entities.Role;
import main.entities.User;
import main.exceptions.ResourceNotFoundException;
import main.repository.RoleRepository;
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
    RoleRepository roleRepository;

    @Autowired
    AuthenticationService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User register(RegisterUserDto userDetails) {
        String roleName = userDetails.getRole();
        Role role = roleRepository.findByName(roleName);

        if(role == null) {
            throw new ResourceNotFoundException("Role not found: "+ userDetails.getRole());
        }

        User user = new User();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setMobile(userDetails.getMobile());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    public User authenticate(LoginDto authDetails) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDetails.getEmail(),
                        authDetails.getPassword()
                )
        );
        User user = userRepository.findByEmail(authDetails.getEmail());
        System.out.println("user0--------"+user);

        return user;
    }
}
