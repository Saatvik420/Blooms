package in.codingage.blooms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.codingage.blooms.models.User;
import in.codingage.blooms.service.UserService;

@RestController
@RequestMapping("/api")
@org.springframework.web.bind.annotation.CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    // Existing /api/users/register
    @PostMapping("/users/register")
    public User registerUser(@RequestParam String username, @RequestParam String email,
                             @RequestParam String name, @RequestParam String password,
                             @RequestParam(required = false) String profileUrl,
                             @RequestParam(required = false) String phone) {
        return userService.registerUser(username, email, name, password, profileUrl, phone);
    }

    // New /api/account/signup (for Frontend)
    @PostMapping("/account/signup")
    public User registerAccount(@org.springframework.web.bind.annotation.RequestBody in.codingage.blooms.dto.AccountCreateRequest request) {
        String username = request.getEmail(); 
        return userService.registerUser(username, request.getEmail(), request.getName(), request.getPassword(), null, request.getPhone());
    }

    // New /api/account/login (for Frontend)
    @PostMapping("/account/login")
    public java.util.Map<String, Object> loginAccount(@org.springframework.web.bind.annotation.RequestBody java.util.Map<String, String> loginRequest) {
        String phone = loginRequest.get("phone");
        String password = loginRequest.get("password");

        User user = userService.getUserByPhone(phone);
        
        if (user != null && user.getPassword().equals(password)) {
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone());
            return response;
        }
        return null;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable String id, @RequestParam String name,
                           @RequestParam String email, @RequestParam(required = false) String profileUrl) {
        return userService.updateUser(id, name, email, profileUrl);
    }

    @PutMapping("/users/{id}/password")
    public boolean updatePassword(@PathVariable String id, @RequestParam String oldPassword,
                                  @RequestParam String newPassword) {
        return userService.updatePassword(id, oldPassword, newPassword);
    }

    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}