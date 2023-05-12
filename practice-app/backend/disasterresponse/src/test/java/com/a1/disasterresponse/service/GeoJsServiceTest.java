package com.a1.disasterresponse.service;
import com.a1.disasterresponse.model.GeoJsData;
import com.a1.disasterresponse.model.IpList;
import com.a1.disasterresponse.repository.IpListRepository;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


public class GeoJsServiceTest {
       
    private static final String TEST_IP_ADDRESS = "150.107.224.214";

    @Test
    void testGetLocationData() throws Exception {
        GeoJsService geoJsService = new GeoJsService();
        GeoJsData geoJsData = geoJsService.getLocationData(TEST_IP_ADDRESS);

        assertEquals(TEST_IP_ADDRESS, geoJsData.getIpAddress());
        assertEquals("Turkey", geoJsData.getCountry());
        assertEquals("Ankara", geoJsData.getCity());
        assertEquals("Ankara", geoJsData.getRegion());
        assertEquals("Europe/Istanbul", geoJsData.getTimezone());
        assertNotNull(geoJsData.getLatitude());
        assertNotNull(geoJsData.getLongitude());
        assertNotNull(geoJsData.getOrganization());
    }

    @Test
    // test created
	void testSaveIp() {
        // create a new IpList object with an IP address
        IpList ipList1 = new IpList();
        ipList1.setIp("150.107.224.214");

        // create a new IpListRepository object
        IpListRepository ipRepository = Mockito.mock(IpListRepository.class);

        // add the IpList object to the repository
        when(ipRepository.save(ipList1)).thenReturn(ipList1);
    
        GeoJsService geoJsService = new GeoJsService(ipRepository);
    
        String answer = geoJsService.saveIp("150.107.224.214");
        assertEquals(answer, "CREATED");


}
}
