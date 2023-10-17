package com.groupa1.resq.entity.enums;

import com.groupa1.resq.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum EUserRole {
    ADMIN(), COORDINATOR, FACILITATOR, RESPONDER, VICTIM;

    public static EUserRole getEnumByStr(String roleStr) {
        try {
            return EUserRole.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            log.error("User role {} not found", roleStr);
            throw new EntityNotFoundException("User role " + roleStr + " is not found");
        }
    }
}
