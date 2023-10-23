package com.groupa1.resq.service;

import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EUserRole;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void requestRole(Long userId, String role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Set<EUserRole> roles = user.getRoles();
        // In the future, users will be checked if they are allowed to take this role.
        roles.add(EUserRole.getEnumByStr(role.toUpperCase()));
        user.setRoles(roles);
        save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void viewRequest(Long userId, Long requestId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        
    }
}
