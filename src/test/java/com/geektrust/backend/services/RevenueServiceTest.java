package com.geektrust.backend.services;

import com.geektrust.backend.dtos.TotalCollection;
import com.geektrust.backend.entities.PassengerCount;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.StationName;
import com.geektrust.backend.services.IPassengerService;
import com.geektrust.backend.services.RevenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RevenueServiceTest {

    private RevenueService revenueService;

    @Mock
    private IPassengerService passengerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        revenueService = new RevenueService(passengerService);
    }

    @Test
    public void testPrintSummary() {
        // Arrange
        Map<String, Integer> stationAmountMap = new HashMap<>();
        stationAmountMap.put(StationName.AIRPORT.name(), 100);
        stationAmountMap.put(StationName.CENTRAL.name(), 200);

        Map<String, Integer> stationDiscountMap = new HashMap<>();
        stationDiscountMap.put(StationName.AIRPORT.name(), 10);
        stationDiscountMap.put(StationName.CENTRAL.name(), 20);

        Map<PassengerType, Integer> passengerTypeCountAirport = new HashMap<>();
        passengerTypeCountAirport.put(PassengerType.ADULT, 5);
        passengerTypeCountAirport.put(PassengerType.KID, 3);
        passengerTypeCountAirport.put(PassengerType.SENIOR_CITIZEN, 2);

        Map<PassengerType, Integer> passengerTypeCountCentral = new HashMap<>();
        passengerTypeCountCentral.put(PassengerType.ADULT, 10);
        passengerTypeCountCentral.put(PassengerType.KID, 6);
        passengerTypeCountCentral.put(PassengerType.SENIOR_CITIZEN, 4);

        Map<StationName, Map<PassengerType, Integer>> stationTypeCountMap = new HashMap<>();
        stationTypeCountMap.put(StationName.AIRPORT, passengerTypeCountAirport);
        stationTypeCountMap.put(StationName.CENTRAL, passengerTypeCountCentral);

        when(passengerService.getStationAmountMap()).thenReturn(stationAmountMap);
        when(passengerService.getStationDiscountMap()).thenReturn(stationDiscountMap);
        when(passengerService.getStationTypeCountMap()).thenReturn(stationTypeCountMap);

        // Act
        revenueService.printSummary();

        // Assert
        verify(passengerService, times(1)).getStationAmountMap();
        verify(passengerService, times(1)).getStationDiscountMap();
        verify(passengerService, times(1)).getStationTypeCountMap();
    }
}
