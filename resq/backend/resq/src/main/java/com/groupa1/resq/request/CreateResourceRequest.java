package com.groupa1.resq.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupa1.resq.entity.enums.EGender;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateResourceRequest {
    private Long ownerId;
    private String categoryTreeId;
    private Integer quantity;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private EGender gender;

}
