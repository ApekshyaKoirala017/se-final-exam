package tests;

import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Bird;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BirdTests {

    @Test
    void testBirdCreation() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.FEATHERS,
                Gender.MALE,
                Breed.BLUE_JAY,
                new BigDecimal("300"),
                10
        );

        assertEquals(10, bird.getPetStoreId());
        assertEquals(Gender.MALE, bird.getGender());
        assertEquals(Breed.BLUE_JAY, bird.getBreed());
        assertEquals(AnimalType.DOMESTIC, bird.getAnimalType());
    }

    @Test
    void testBirdSpeak() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.FEATHERS,
                Gender.FEMALE,
                Breed.BLUE_JAY,
                new BigDecimal("300"),
                11
        );

        assertTrue(bird.speak().toLowerCase().contains("chirp"));
    }

    @Test
    void testBirdCost() {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.FEATHERS,
                Gender.MALE,
                Breed.BLUE_JAY,
                new BigDecimal("300"),
                12
        );

        assertEquals(new BigDecimal("300"), bird.getCost());
    }
}