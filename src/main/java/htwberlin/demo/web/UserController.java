package htwberlin.demo.web;

import htwberlin.demo.persistence.UserEntity;
import htwberlin.demo.persistence.UserRepository;
import htwberlin.demo.service.UserService;
import htwberlin.demo.web.api.UserLoginRequest;
import htwberlin.demo.web.api.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")

    public class UserController {
    private UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
        public UserController(UserService userService) {
            this.userService = userService;
        }

        @PostMapping("/api/v1/users/register/")
        public ResponseEntity<Void> register(@RequestBody UserRegistrationRequest request) {
            boolean registered = userService.register(request);

            if (!registered) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok().build();
        }

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

        @PostMapping("/api/v1/users/login")
        public ResponseEntity<Void> login(@RequestBody UserLoginRequest request) {
            boolean loggedIn = userService.login(request);

            if (!loggedIn) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.ok().build();
        }
    }
