package com.a1.disasterresponse.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.a1.disasterresponse.model.NasaData;
import com.a1.disasterresponse.repository.NasaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NASAImageService {

    @Autowired
    private final NasaDataRepository nasaDataRepository;

    public NASAImageService(NasaDataRepository nasaDataRepository) {
        this.nasaDataRepository = nasaDataRepository;
    }

    public String searchImages(String text) {
        String endpoint = "https://images-api.nasa.gov/search";
        String searchUrl = endpoint + "?q=" + text + "&media_type=image";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(searchUrl, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
    public void saveNasaData(NasaData nasadata) {
        nasaDataRepository.save(nasadata);
    }
}