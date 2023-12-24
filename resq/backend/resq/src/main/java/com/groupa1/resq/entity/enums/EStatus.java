package com.groupa1.resq.entity.enums;

public enum EStatus {
    TODO, // accepted
    IN_PROGRESS,
    DONE,
    PENDING, //responder assigned, but not yet accepted
    DECLINED,
    FREE // no responder assigned
}
