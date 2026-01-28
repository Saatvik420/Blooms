package in.codingage.blooms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.codingage.blooms.models.User;
import in.codingage.blooms.repository.UserRepository;
import in.codingage.blooms.service.UserService;
import in.codingage.blooms.utils.RandomIdUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(String username, String email, String name, String password, String profileUrl, String phone) {
        User user = new User();
        user.setId(RandomIdUtils.generateRandomId(10));
        user.setUsername(username);
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password); // Storing plain text as per current controller logic
        user.setProfileUrl(profileUrl);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String id, String name, String email, String profileUrl) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (profileUrl != null) user.setProfileUrl(profileUrl);
        return userRepository.save(user);
    }

    @Override
    public boolean updatePassword(String id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
