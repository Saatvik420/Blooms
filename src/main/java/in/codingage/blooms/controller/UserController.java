package in.codingage.blooms.controller;

import in.codingage.blooms.dto.AccountCreateRequest;
import in.codingage.blooms.models.User;
import in.codingage.blooms.repository.UserRepository;
import in.codingage.blooms.utils.RandomIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/account/signup")
    public void createUser(@RequestBody AccountCreateRequest accountCreateRequest){
        // Implementation for user creation goes here
        User user = new User();
        user.setEmail(accountCreateRequest.getEmail());
        user.setId(RandomIdUtils.generateRandomId(6));
        user.setName(accountCreateRequest.getName());
        user.setPassword(accountCreateRequest.getPassword());
        System.out.println("api called");
        userRepository.save(user);
        return;
    }


    @GetMapping("/api/account")
    public User getUserByName(@RequestParam String name){
        return userRepository.findByName(name);
    }


    @GetMapping("/api/account/{id}")
    public User getUserById(@PathVariable(value = "id") String name){
        return userRepository.findById(name).orElse(null);
    }



}
