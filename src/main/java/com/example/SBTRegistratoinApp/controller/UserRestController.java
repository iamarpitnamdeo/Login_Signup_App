package com.example.SBTRegistratoinApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SBTRegistratoinApp.entity.UserDtls;
import com.example.SBTRegistratoinApp.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDtls> registerUser(@RequestBody UserDtls userDtls) {
        UserDtls savedUser = userService.saveUser(userDtls);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtls> getUserById(@PathVariable int id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDtls> updateUser(@PathVariable int id, @RequestBody UserDtls userDtls) {
        UserDtls updatedUser = userService.updateUser(id, userDtls);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDtls>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
