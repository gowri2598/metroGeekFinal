package com.geektrust.backend.repositories;

import com.geektrust.backend.constants.Common;
import com.geektrust.backend.entities.CheckIn;
import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.entities.PassengerType;
import com.geektrust.backend.entities.StationName;
import com.geektrust.backend.repositories.PassengerRepository;
import com.geektrust.backend.services.IMetroCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PassengerRepositoryTest {

    private PassengerRepository passengerRepository;
    private IMetroCardService metroCardService;

    @BeforeEach
    public void setup() {
        metroCardService = Mockito.mock(IMetroCardService.class);
        passengerRepository = new PassengerRepository(metroCardService);
    }

    @Test
    public void testCheckInPassenger() {
        // Arrange
        String cardId = "1234";
        CheckIn checkIn = new CheckIn(cardId,  PassengerType.ADULT.toString(),StationName.AIRPORT.toString());
        Mockito.when(metroCardService.transactCard(Mockito.anyString(), Mockito.anyInt())).thenReturn(100);

        // Act
        passengerRepository.checkInPassenger(checkIn);

        // Assert
        Map<String, Integer> amountMap = passengerRepository.getStationAmountMap();
        Map<String, Integer> discountMap = passengerRepository.getStationDiscountMap();
        Map<StationName, Map<PassengerType, Integer>> typeCountMap = passengerRepository.getStationTypeCountMap();
        
        assertNotNull(amountMap);
        assertNotNull(discountMap);
        assertNotNull(typeCountMap);

        assertEquals(Common.ZERO, amountMap.get(StationName.CENTRAL.name()));
        assertEquals(Common.ZERO, discountMap.get(StationName.CENTRAL.name()));
        assertEquals(1, typeCountMap.get(StationName.AIRPORT).get(PassengerType.ADULT).intValue());
        
        // assertEquals(PassengerType.ADULT.getVal(), amountMap.get(StationName.AIRPORT.name()));
        assertEquals(Common.ZERO, discountMap.get(StationName.AIRPORT.name()));
    }
}
