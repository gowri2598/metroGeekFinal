package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.MetroCard;
import com.geektrust.backend.repositories.MetroCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetroCardRepositoryTest {

    private MetroCardRepository repository;

    @BeforeEach
    public void setup() {
        repository = new MetroCardRepository();
    }

    @Test
    public void testSave() {
        // Arrange
        MetroCard card = new MetroCard("1234", 100);

        // Act
        repository.save(card);

        // Assert
        MetroCard savedCard = repository.getCard(card.getCardId());
        assertNotNull(savedCard);
        assertEquals(card.getCardId(), savedCard.getCardId());
        assertEquals(card.getBalance(), savedCard.getBalance());
    }

    @Test
    public void testFindAll() {
        // Arrange
        MetroCard card1 = new MetroCard("1234", 100);
        MetroCard card2 = new MetroCard("5678", 150);
        repository.save(card1);
        repository.save(card2);

        // Act
        List<MetroCard> allCards = repository.findAll();

        // Assert
        assertEquals(2, allCards.size());
        assertTrue(allCards.stream().map(c1 -> {
            return c1.getCardId();
        }).collect(Collectors.toList()).contains(card1.getCardId()));
        assertTrue(allCards.stream().map(c2 -> {
            return c2.getCardId();
        }).collect(Collectors.toList()).contains(card2.getCardId()));    }

    @Test
    public void testGetCard() {
        // Arrange
        MetroCard card = new MetroCard("1234", 100);
        repository.save(card);

        // Act
        MetroCard retrievedCard = repository.getCard(card.getCardId());

        // Assert
        assertNotNull(retrievedCard);
        assertEquals(card.getCardId(), retrievedCard.getCardId());
        assertEquals(card.getBalance(), retrievedCard.getBalance());
    }
}
