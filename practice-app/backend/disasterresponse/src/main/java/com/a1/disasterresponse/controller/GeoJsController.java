package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.service.GeoJsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import com.a1.disasterresponse.model.GeoJsData;

import com.a1.disasterresponse.model.IpList;

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


            GeoJsData geoJsData = geoJsService.getLocationData(ipAddress);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("latitude", geoJsData.getLatitude());
            responseMap.put("longitude", geoJsData.getLongitude());
            responseMap.put("ipAddress", geoJsData.getIpAddress());
            responseMap.put("country", geoJsData.getCountry());
            responseMap.put("region", geoJsData.getRegion());
            responseMap.put("city", geoJsData.getCity());
            responseMap.put("timezone", geoJsData.getTimezone());
            responseMap.put("organization", geoJsData.getOrganization());
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            // status 500 is Internal Server Error
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/getIpList")
	public ResponseEntity<List<IpList>> getIpList() {
		return new ResponseEntity<List<IpList>>(geoJsService.getIpList() ,HttpStatus.OK);
	}	
	
	@PostMapping("/saveIp")
	public ResponseEntity<String> saveIp(@RequestParam String ip) {
		return ResponseEntity.ok(geoJsService.saveIp(ip));
	}


}