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

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User requestRole(Long userId, String role) {
        User user = findById(userId);
        Set<EUserRole> roles = user.getRoles();
        // In the future, users will be checked if they are allowed to take this role.
        roles.add(EUserRole.getEnumByStr(role.toUpperCase()));
        user.setRoles(roles);
        return save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
