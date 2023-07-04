package com.geektrust.backend.services;

import com.geektrust.backend.entities.CheckIn;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.StationName;
import com.geektrust.backend.repositories.IMetroCardRepository;
import com.geektrust.backend.repositories.IPassengerRepository;
import com.geektrust.backend.repositories.IRevenueRepository;
import com.geektrust.backend.services.IMetroCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PassengerServiceTest {

    private PassengerService passengerService;

    @Mock
    private IMetroCardRepository metroCardRepository;
    
    @Mock
    private IPassengerRepository passengerRepository;

    @Mock
    private IRevenueRepository revenueRepository;

    @Mock
    private IMetroCardService metroCardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        passengerService = new PassengerService(metroCardRepository, passengerRepository, revenueRepository, metroCardService);
    }

    @Test
    public void testCheckInPassenger() {
        String cardId = "card123";
        String passengerType = "ADULT";
        String fromStation = "CENTRAL";
        CheckIn checkIn = new CheckIn(cardId, passengerType, fromStation);

        passengerService.checkInPassenger(cardId, passengerType, fromStation);

        verify(passengerRepository, times(1)).checkInPassenger(any(CheckIn.class));
    }

    @Test
    public void testGetStationTypeCountMap() {
        Map<StationName, Map<PassengerType, Integer>> expectedMap = Collections.emptyMap();
        Mockito.when(passengerRepository.getStationTypeCountMap()).thenReturn(expectedMap);

        Map<StationName, Map<PassengerType, Integer>> resultMap = passengerService.getStationTypeCountMap();

        assertEquals(expectedMap, resultMap);
    }

    // Add more tests here for the other methods in your service
}
