package com.groupa1.resq.jobs;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.repository.NeedRepository;
import com.groupa1.resq.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
public class RecurrentNeedJob {

    @Autowired
    private NeedRepository needRepository;

    @Autowired
    private RequestRepository requestRepository;

    // will be open later
    //@Scheduled(cron = "0 0 8 * * ?")
    public void run() {
        // This job will create a request consisting of recurrent needs every day.
        HashSet<Request> requests = new HashSet<>();
        needRepository.findAllByIsRecurrentTrue().forEach(need -> {
            Request request = need.getRequest();
            if(!requests.contains(request)) {
                Request newRequest = new Request();
                newRequest.setNeeds(new HashSet<>());
                newRequest.setRequester(request.getRequester());
                newRequest.setDescription(request.getDescription());
                newRequest.setLongitude(request.getLongitude());
                newRequest.setLatitude(request.getLatitude());
                newRequest.setUrgency(request.getUrgency());
                newRequest.setStatus(request.getStatus());

                request.getNeeds().forEach(need1 -> {
                    if (need1.getIsRecurrent()) {
                        Need newNeed = new Need();
                        newNeed.setIsRecurrent(false);
                        newNeed.setLatitude(need1.getLatitude());
                        newNeed.setLongitude(need1.getLongitude());
                        newNeed.setQuantity(need1.getQuantity());
                        newNeed.setSize(need1.getSize());
                        newNeed.setDescription(need1.getDescription());
                        newNeed.setStatus(ENeedStatus.NOT_INVOLVED);
                        newNeed.setRequester(need1.getRequester());
                        newNeed.setCategoryTreeId(need1.getCategoryTreeId());
                        newNeed.setRequest(newRequest);
                        needRepository.save(newNeed);
                        newRequest.getNeeds().add(newNeed);
                    }

                });
                requests.add(request);
                requestRepository.save(newRequest);
            }
        });
        log.info("Recurrent Need Job");
        System.out.println("Recurrent Need Job");
    }
}
