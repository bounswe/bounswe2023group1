package com.groupa1.resq.converter;

import com.groupa1.resq.dto.RequestDto;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.RequestRepository;
import com.groupa1.resq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequestConverter {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NeedRepository needRepository;

    public RequestDto convertToDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setRequesterId(request.getRequester().getId());
        List<Need> needs = request.getNeeds().stream().toList();
        requestDto.setNeedIds(needs.stream().map(Need::getId).toList());
        requestDto.setLatitude(request.getLatitude());
        requestDto.setLongitude(request.getLongitude());
        requestDto.setStatus(request.getStatus());
        requestDto.setDescription(request.getDescription());
        return requestDto;
    }

    public Request convertToEntity(RequestDto requestDto) {
        Request request = new Request();
        User user = userRepository.findById(requestDto.getRequesterId()).orElse(null);
        Set<Need> needs = new HashSet<>(needRepository.findAllById(requestDto.getNeedIds()));
        request.setRequester(user);
        request.setId(requestDto.getId());
        request.setNeeds(needs);
        request.setLatitude(requestDto.getLatitude());
        request.setLongitude(requestDto.getLongitude());
        request.setUrgency(requestDto.getUrgency());
        request.setStatus(requestDto.getStatus());
        request.setDescription(requestDto.getDescription());
        return request;
    }


}
