package com.ajaz.librarymanagementsystem.services;

import com.ajaz.librarymanagementsystem.exceptions.UserNotFoundException;
import com.ajaz.librarymanagementsystem.models.User;
import com.ajaz.librarymanagementsystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User createUser(User user){

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id = " + id + " does not exist.");
        }

        return userOptional.get();
    }
}
