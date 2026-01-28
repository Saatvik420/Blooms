package in.codingage.blooms.controller;

import in.codingage.blooms.models.User;
import in.codingage.blooms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    private Map<String, String> activeSessions = new HashMap<>(); // userId -> sessionToken

    // Login user
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            // Try login with email
            user = userService.getUserByEmail(username);
        }

        if (user != null && user.getPassword().equals(password)) {
            // Generate session token
            String sessionToken = generateSessionToken(user.getId());
            activeSessions.put(user.getId(), sessionToken);
            System.out.println("Login successful for user: " + user.getUsername());
            return sessionToken;
        }

        System.out.println("Login failed: Invalid credentials");
        return null;
    }

    // Logout user
    @PostMapping("/logout")
    public boolean logout(@RequestParam String userId) {
        if (activeSessions.containsKey(userId)) {
            activeSessions.remove(userId);
            System.out.println("User logged out successfully");
            return true;
        }
        return false;
    }

    // Verify session
    @GetMapping("/verify")
    public boolean isSessionValid(@RequestParam String userId, @RequestParam String sessionToken) {
        String storedToken = activeSessions.get(userId);
        return storedToken != null && storedToken.equals(sessionToken);
    }

    // Generate a simple session token
    private String generateSessionToken(String userId) {
        return "TOKEN_" + userId + "_" + System.currentTimeMillis();
    }

    // Get all active sessions (for admin)
    @GetMapping("/sessions")
    public Map<String, String> getActiveSessions() {
        return new HashMap<>(activeSessions);
    }
}