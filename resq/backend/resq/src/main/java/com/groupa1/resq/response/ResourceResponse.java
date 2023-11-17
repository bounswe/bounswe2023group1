package com.groupa1.resq.response;

import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EGender;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ResourceResponse {
    private long id;
    private User sender;
    private int quantity;
    private EGender gender;
    private String categoryId;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
