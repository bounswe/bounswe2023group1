package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.service.GeoJsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Map;

@RestController
public class GeoJsController {

    @Autowired
    private GeoJsService geoJsService;

    @GetMapping("/get_info_from_ip")
    public ResponseEntity<Map<String, Object>> getLocationData(@RequestParam("ip") String ipAddress) {
        try {
            try{
                InetAddress inet = InetAddress.getByName(ipAddress);
                if (! ((inet instanceof Inet4Address) || (inet instanceof Inet6Address))) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "API supports IPv4 and IPv6."));
                }
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid IP address."));
            }


            Map<String, Object> response = geoJsService.getLocationData(ipAddress);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            // status 500 is Internal Server Error
            return ResponseEntity.status(500).build();
        }
    }
}
