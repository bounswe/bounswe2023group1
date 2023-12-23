package com.groupa1.resq.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.ESize;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateResourceRequest {
    private Long senderId;
    private String categoryTreeId;
    private Integer quantity;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private EGender gender;
    private ESize size;
}
