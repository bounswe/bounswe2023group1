package com.groupa1.resq.response;

import com.groupa1.resq.dto.NeedDto;
import com.groupa1.resq.dto.RequestDto;
import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.entity.enums.ERequestStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NeedStatusResponse {
    private Long needId;
    private Long requestId;
    private ENeedStatus needStatus;
    private ERequestStatus requestStatus;
    private Long RequestCreatorId;
    private BigDecimal requestLatitude;
    private BigDecimal requestLongitude;

}
