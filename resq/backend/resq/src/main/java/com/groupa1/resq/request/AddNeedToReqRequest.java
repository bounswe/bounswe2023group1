package com.groupa1.resq.request;

import lombok.Data;

import java.util.List;

@Data
public class AddNeedToReqRequest {
    private Long requestId;
    private List<Long> needIds;
}
