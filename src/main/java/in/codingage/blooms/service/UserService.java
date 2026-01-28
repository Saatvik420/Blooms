package in.codingage.blooms.service;

import java.util.List;

import in.codingage.blooms.models.User;

public interface UserService {
    User registerUser(String username, String email, String name, String password, String profileUrl, String phone);

    User getUserById(String id);

    List<User> getAllUsers();

    User updateUser(String id, String name, String email, String profileUrl);

    boolean updatePassword(String id, String oldPassword, String newPassword);

    boolean deleteUser(String id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByPhone(String phone);
}
