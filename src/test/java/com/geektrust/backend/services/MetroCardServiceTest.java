package com.geektrust.backend.services;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.exceptions.CardNumberNotFoundException;
import com.geektrust.backend.repositories.IMetroCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetroCardServiceTest {

    private IMetroCardRepository mockRepository;
    private MetroCardService metroCardService;

    @BeforeEach
    public void setup() {
        mockRepository = mock(IMetroCardRepository.class);
        metroCardService = new MetroCardService(mockRepository);
    }

    @Test
    public void testAddCard() {
        String cardId = "Card1";
        int balance = 100;

        metroCardService.addCard(cardId, balance);
        verify(mockRepository, times(1)).save(any(MetroCard.class));
    }

    @Test
    public void testGetCards() {
        List<MetroCard> cards = new ArrayList<>(Arrays.asList(new MetroCard("Card1", 100), new MetroCard("Card2", 200)));
        when(mockRepository.findAll()).thenReturn(cards);
        assertEquals(cards, metroCardService.getCards());
    }

    @Test
    public void testTransactCard() {
        String cardId = "Card1";
        int balance = 100;
        int amount = 50;

        MetroCard metroCard = new MetroCard(cardId, balance);
        when(mockRepository.getCard(cardId)).thenReturn(metroCard);
        
        int remainingBalance = metroCardService.transactCard(cardId, amount);
        assertEquals(0, remainingBalance);
        assertEquals(balance - amount, metroCard.getBalance());
        verify(mockRepository, times(1)).save(metroCard);
    }

    @Test
    public void testTransactCard_InsufficientBalance() {
        String cardId = "Card1";
        int balance = 100;
        int amount = 150;

        MetroCard metroCard = new MetroCard(cardId, balance);
        when(mockRepository.getCard(cardId)).thenReturn(metroCard);

        int remainingBalance = metroCardService.transactCard(cardId, amount);
        assertEquals(50, remainingBalance);
        assertEquals(0, metroCard.getBalance());
        verify(mockRepository, times(1)).save(metroCard);
    }

    @Test
    public void testTransactCard_CardNotFound() {
        String cardId = "Card1";
        int amount = 50;

        when(mockRepository.getCard(cardId)).thenReturn(null);
        assertThrows(CardNumberNotFoundException.class, () -> {
            metroCardService.transactCard(cardId, amount);
        });
    }
}
