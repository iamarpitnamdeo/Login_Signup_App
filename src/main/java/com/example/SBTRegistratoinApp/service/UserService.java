package com.example.SBTRegistratoinApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.SBTRegistratoinApp.entity.UserDtls;
import com.example.SBTRegistratoinApp.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserDtls saveUser(UserDtls userDtls) {
        return userRepository.save(userDtls);
    }

    public Optional<UserDtls> findUserById(int id) {
        return userRepository.findById(id);
    }

    public List<UserDtls> findAllUsers() {
        return userRepository.findAll();
    }

    public UserDtls updateUser(int id, UserDtls userDtls) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFullname(userDtls.getFullname());
            existingUser.setGender(userDtls.getGender());
            existingUser.setDob(userDtls.getDob());
            existingUser.setAddress(userDtls.getAddress());
            existingUser.setEmail(userDtls.getEmail());
            existingUser.setPassword(userDtls.getPassword());
            existingUser.setRole(userDtls.getRole());
            return userRepository.save(existingUser);
        }).orElse(null);
    }

    public boolean deleteUser(int id) {
        return userRepository.findById(id).map(user -> {
            userRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}