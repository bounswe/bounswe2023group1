package com.groupa1.resq.dto;

import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.ESize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String categoryTreeId;
    private EGender gender;
    private Integer quantity;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime createdDate;
    private ESize size;
    private FileDto file;
}
