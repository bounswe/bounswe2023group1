package com.groupa1.resq.converter;

import com.groupa1.resq.dto.NeedDto;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NeedConverter {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    public NeedDto convertToDto(Need need) {
        NeedDto needDto = new NeedDto();
        needDto.setId(need.getId());
        needDto.setUserId(need.getRequester().getId());
        needDto.setCategoryTreeId(need.getCategoryTreeId());
        needDto.setDescription(need.getDescription());
        needDto.setQuantity(need.getQuantity());
        needDto.setLatitude(need.getLatitude());
        needDto.setLongitude(need.getLongitude());
        needDto.setCreatedDate(need.getCreatedAt());
        Request request = need.getRequest();
        if (request != null) {
            needDto.setRequestId(request.getId());
        }
        needDto.setStatus(need.getStatus());
        return needDto;
    }

    public Need convertToEntity(NeedDto needDto) {
        Need need = new Need();
        User user = userRepository.findById(needDto.getUserId()).orElse(null);
        need.setId(needDto.getId());
        need.setRequester(user);
        need.setCategoryTreeId(needDto.getCategoryTreeId());
        need.setDescription(needDto.getDescription());
        need.setQuantity(needDto.getQuantity());
        need.setLatitude(needDto.getLatitude());
        need.setLongitude(needDto.getLongitude());
        need.setStatus(needDto.getStatus());
        need.setCreatedAt(needDto.getCreatedDate());
        Request request = requestRepository.findById(needDto.getRequestId()).orElse(null);
        need.setRequest(request);
        return need;
    }


}
