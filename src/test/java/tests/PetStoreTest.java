package tests;

import animals.AnimalType;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;
import animals.petstore.pet.types.Bird;
import animals.petstore.store.DuplicatePetStoreRecordException;
import animals.petstore.store.PetNotFoundSaleException;
import animals.petstore.store.PetStore;
import number.Numbers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class PetStoreTest
{
    private static PetStore petStore;

    @BeforeEach
    public void loadThePetStoreInventory()
    {
        petStore = new PetStore();
        petStore.init();
    }

    @Test
    @DisplayName("Inventory Count Test")
    public void validateInventory()
    {
        assertEquals(5, petStore.getPetsForSale().size(),"Inventory counts are off!");
    }

    @Test
    @DisplayName("Print Inventory Test")
    public void printInventoryTest()
    {
        petStore.printInventory();
    }

    @Test
    @DisplayName("Sale of Poodle Remove Item Test")
    public void poodleSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Dog poodle = new Dog(
                AnimalType.DOMESTIC,
                Skin.FUR,
                Gender.MALE,
                Breed.POODLE,
                new BigDecimal("650.00"),
                1
        );

        petStore.soldPetItem(poodle);
        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
    }


    @Test
    @DisplayName("Poodle Duplicate Record Exception Test")
    public void poodleDupRecordExceptionTest() throws DuplicatePetStoreRecordException {

        petStore.addPetInventoryItem(new Dog(
                AnimalType.DOMESTIC,
                Skin.FUR,
                Gender.MALE,
                Breed.POODLE,
                new BigDecimal("650.00"),
                1
        ));

        Dog poodle = new Dog(
                AnimalType.DOMESTIC,
                Skin.FUR,
                Gender.MALE,
                Breed.POODLE,
                new BigDecimal("650.00"),
                1
        );

        String expectedMessage = "Duplicate Dog record store id [1]";

        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () ->{
            petStore.soldPetItem(poodle);
        });

        assertEquals(expectedMessage, exception.getMessage(), "DuplicateRecordExceptionTest was NOT encountered!");
    }

    @Test
    @DisplayName("Sale of Sphynx Remove Item Test")
    public void sphynxSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.FEMALE,
                Breed.SPHYNX,
                new BigDecimal("100.00"),
                2
        );

        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Expected inventory does not match actual");
        assertEquals(sphynx.getPetStoreId(), removedItem.getPetStoreId(), "The cat items are identical");
    }

    @TestFactory
    @DisplayName("Sale of Sphynx Remove Item Test2")
    public Stream<DynamicNode> sphynxSoldTest2() throws DuplicatePetStoreRecordException, PetNotFoundSaleException {
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Cat sphynx = new Cat(
                AnimalType.DOMESTIC,
                Skin.UNKNOWN,
                Gender.FEMALE,
                Breed.SPHYNX,
                new BigDecimal("100.00"),
                2
        );

        Cat removedItem = (Cat) petStore.soldPetItem(sphynx);

        List<DynamicNode> nodes = new ArrayList<>();
        List<DynamicTest> dynamicTests = Arrays.asList(
                dynamicTest("Inventory Check Size Test", () ->
                        assertEquals(inventorySize, petStore.getPetsForSale().size())),
                dynamicTest("The cat objects match", () ->
                        assertEquals(sphynx.toString(), removedItem.toString()))
        );

        nodes.add(dynamicContainer("Cat Item 2 Test", dynamicTests));
        return nodes.stream();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, -10, 128, Integer.MIN_VALUE})
    void isNumberEven(int number)
    {
        assertTrue(Numbers.isEven(number));
    }

    @Test
    @DisplayName("Add Bird To Inventory Test")
    public void addBirdTest() throws DuplicatePetStoreRecordException
    {
        int inventorySize = petStore.getPetsForSale().size() + 1;

        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.FEATHERS,
                Gender.MALE,
                Breed.PARROT,
                new BigDecimal("300.00"),
                10
        );

        petStore.addPetInventoryItem(bird);

        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Bird was not added to inventory");
    }

    @Test
    @DisplayName("Sell Bird Test")
    public void birdSoldTest() throws DuplicatePetStoreRecordException, PetNotFoundSaleException
    {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.FEATHERS,
                Gender.FEMALE,
                Breed.PARROT,
                new BigDecimal("300.00"),
                10
        );

        petStore.addPetInventoryItem(bird);
        int inventorySize = petStore.getPetsForSale().size() - 1;

        Bird removedBird = (Bird) petStore.soldPetItem(bird);

        assertEquals(inventorySize, petStore.getPetsForSale().size(), "Bird was not removed after sale");
        assertEquals(bird.getPetStoreId(), removedBird.getPetStoreId(), "Wrong Bird removed from store");
    }


    @Test
    @DisplayName("Bird Duplicate Record Exception Test")
    public void birdDuplicateRecordExceptionTest() throws DuplicatePetStoreRecordException
    {
        Bird bird = new Bird(
                AnimalType.DOMESTIC,
                Skin.FEATHERS,
                Gender.MALE,
                Breed.PARROT,
                new BigDecimal("300.00"),
                10
        );

        petStore.addPetInventoryItem(bird);

        Exception exception = assertThrows(DuplicatePetStoreRecordException.class, () ->{
            petStore.addPetInventoryItem(bird);
        });

        assertTrue(exception.getMessage().contains("Duplicate"), "Duplicate bird exception not thrown");
    }
}
