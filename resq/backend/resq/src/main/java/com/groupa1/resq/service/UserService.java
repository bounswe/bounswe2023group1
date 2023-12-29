package com.groupa1.resq.service;

import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EUserRole;
import com.groupa1.resq.exception.EntityNotAllowedException;
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

    public User assignRole(Long assignerId, Long assigneeId, String role) {
        if (checkUserHierarchy(assignerId, role)) {
            return requestRole(assigneeId, role);
        }else{
            throw new EntityNotAllowedException("User not allowed to assign this role");
        }
    }

    public User removeRole(Long assignerId, Long assigneeId, String role) {
        if (checkUserHierarchy(assignerId, role)) {
            User user = userRepository.findById(assigneeId).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Set<EUserRole> roles = user.getRoles();
            roles.remove(EUserRole.getEnumByStr(role.toUpperCase()));
            user.setRoles(roles);
            return save(user);
        } else {
            throw new EntityNotAllowedException("User not allowed to remove this role");
        }
    }


    private boolean checkUserHierarchy(Long userId, String role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Set<EUserRole> roles = user.getRoles();
        EUserRole roleEnum = EUserRole.getEnumByStr(role.toUpperCase());
        if (roles.contains(EUserRole.ADMIN)) {
            return true;
        }else if(roles.contains(EUserRole.COORDINATOR)){
            return roleEnum != EUserRole.ADMIN;
        }else{
            return roleEnum != EUserRole.ADMIN && roleEnum != EUserRole.COORDINATOR;
        }
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
