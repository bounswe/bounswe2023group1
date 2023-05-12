package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.GeocodeData;
import com.a1.disasterresponse.service.GeocodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GeocodeController.class)
public class GeocodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeocodeService geocodeService;

    private GeocodeData geocodeData;

    @BeforeEach
    public void setUp() {
        geocodeData = new GeocodeData(40.7128, -74.0060, "New York");
    }

    @Test
    public void testGeocode() throws Exception {
        when(geocodeService.geocodeAddress("New York")).thenReturn(geocodeData);

        mockMvc.perform(get("/geocode")
                .param("address", "New York"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("latitude").value(40.7128))
                .andExpect(jsonPath("longitude").value(-74.0060))
                .andExpect(jsonPath("address").value("New York"));
    }

    @Test
    public void testReverseGeocode() throws Exception {
        when(geocodeService.reverseGeocodeCoordinates(40.7128, -74.0060)).thenReturn(geocodeData);

        mockMvc.perform(get("/reverse_geocode")
                .param("latitude", "40.7128")
                .param("longitude", "-74.0060"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("latitude").value(40.7128))
                .andExpect(jsonPath("longitude").value(-74.0060))
                .andExpect(jsonPath("address").value("New York"));
    }

    @Test
    public void testSaveGeocodeData() throws Exception {
        when(geocodeService.saveGeocodeData(any(GeocodeData.class))).thenReturn(geocodeData);

        mockMvc.perform(post("/geocode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"latitude\":40.7128,\"longitude\":-74.0060,\"address\":\"New York\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Geocode data saved successfully"));
    }
}
