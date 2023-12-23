package com.groupa1.resq.controller;


import com.groupa1.resq.request.CreateEventRequest;
import com.groupa1.resq.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.groupa1.resq.response.EventResponse;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR') or hasRole('FACILITATOR') or hasRole('VICTIM')")
    @PostMapping("/createEvent")
    public ResponseEntity<String> createEvent(@RequestBody CreateEventRequest createEventRequest){
        return eventService.createEvent(createEventRequest);
    }

    @PreAuthorize("hasRole('RESPONDER') or hasRole('COORDINATOR') or hasRole('FACILITATOR') or hasRole('VICTIM')")
    @GetMapping("/viewEvents")
    public ResponseEntity<List<EventResponse>> viewEvents(@RequestParam Long reporterId) {
        return eventService.viewEvents( reporterId);
    }


}
