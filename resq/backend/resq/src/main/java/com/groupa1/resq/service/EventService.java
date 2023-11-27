package com.groupa1.resq.service;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.groupa1.resq.entity.Event;
import com.groupa1.resq.entity.Event;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.EventRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateEventRequest;
import com.groupa1.resq.request.CreateEventRequest;
import com.groupa1.resq.response.EventResponse;
import com.groupa1.resq.response.EventResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<String> createEvent(CreateEventRequest createEventRequest) {

        // creating event for a user
        Event eventEntity = new Event();
        User reporter = userRepository.findById(createEventRequest.getReporterId()).orElseThrow(()-> new EntityNotFoundException("No user found"));
        String description = createEventRequest.getDescription();
        BigDecimal startLatitude = createEventRequest.getEventLatitude();
        BigDecimal startLongitude =createEventRequest.getEventLongitude();
        eventEntity.setReporter(reporter);
        eventEntity.setVerified(false); // default
        eventEntity.setDescription(description);
        eventEntity.setEventLatitude(startLatitude);
        eventEntity.setEventLongitude(startLongitude);
        eventEntity.setCreatedAt(LocalDateTime.now());
        
        
        eventRepository.save(eventEntity);
        return ResponseEntity.ok("Event saved successfully!");

    }

    public ResponseEntity<List<EventResponse>> viewEvents(Long reporterId){
        Optional<User> reporter = userRepository.findById(reporterId);

        List<EventResponse> eventResponses = new ArrayList<>();
        if (reporter.isPresent()) {
            Set<Event> events = reporter.get().getReportedEvents();
            events.forEach(event -> {
                EventResponse eventResponse = new EventResponse();
                eventResponse.setId(event.getId())
                        .setReporterId(event.getReporter().getId())
                        .setDescription(event.getDescription())
                        .setCompleted(event.isVerified())
                        .setEventLatitude(event.getEventLatitude())
                        .setEventLongitude(event.getEventLongitude())
                        .setReportDate(event.getReportDate());
                eventResponses.add(eventResponse);
            });
            return ResponseEntity.ok(eventResponses);
        } else {
            log.error("No user found with id: {}", reporterId);
            return null;
        }
    }




}
