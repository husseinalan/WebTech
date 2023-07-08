package htwberlin.demo.service;

import htwberlin.demo.persistence.UserEntity;
import htwberlin.demo.persistence.UserRepository;
import htwberlin.demo.web.api.UserLoginRequest;
import htwberlin.demo.web.api.UserRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(UserRegistrationRequest request) {
        UserEntity existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser != null) {
            return false;
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        userRepository.save(newUser);

        return true;
    }

    public boolean login(UserLoginRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return false;
        }

        return request.getPassword().equals(user.getPassword());
    }
}
