package tests;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Dog;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestExampleTest
{
    private List<Pet> dListActual = Arrays.asList(
            new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.MALTESE,
                    new BigDecimal("750.00"), 1),
            new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                    new BigDecimal("750.00"), 2),
            new Dog(AnimalType.DOMESTIC, Skin.HAIR, Gender.FEMALE, Breed.GERMAN_SHEPARD,
                    new BigDecimal("750.00"), 2));

    private List<Pet> dListExpected = Arrays.asList(
            new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.MALTESE,
                    new BigDecimal("750.00"), 1),
            new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                    new BigDecimal("750.00"), 2),
            new Dog(AnimalType.DOMESTIC, Skin.HAIR, Gender.FEMALE, Breed.CARDINAL,
                    new BigDecimal("750.00"), 2)
    );

    @Test
    @Disabled
    @DisplayName("ABC test")
    void abcTest()
    {
        assertThat("abc", is(123));
    }

    @Test
    @DisplayName("Empty String test")
    void emptyStringTest()
    {
        // ✅ Hamcrest 1.3 compatible
        assertThat("", is(""));
    }

    @Test
    @DisplayName("Collection Test not null")
    void dogCollectionNotNull()
    {
        assertThat(dListActual, notNullValue());
    }

    @Test
    @DisplayName("Collection Test not empty")
    void dogCollectionNotEmpty()
    {
        assertThat(dListActual, not(empty()));
    }

    @Test
    @Disabled
    @DisplayName("Dog Collection Match Tests1")
    void dogCollectionMatch()
    {
        assertThat(dListActual, is(dListExpected));
    }

    @Test
    @DisplayName("Dog Collection Match Tests2")
    void dogCollectionSameListTest()
    {
        assertThat(dListActual, is(dListActual));
    }
}