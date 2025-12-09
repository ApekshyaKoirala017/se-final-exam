package animals.petstore.store;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;
import animals.petstore.pet.types.Cat;
import animals.petstore.pet.types.Dog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PetStore
{
    private List<Pet> petsForSale;
    private List<Pet> petsSold;

    public PetStore()
    {
        petsForSale = new ArrayList<>();
        petsSold = new ArrayList<>();
    }

    public void init()
    {
        this.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.MALTESE,
                new BigDecimal("750.00"), 3));

        this.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.FUR, Gender.MALE, Breed.POODLE,
                new BigDecimal("650.00"), 1));

        this.addPetInventoryItem(new Cat(AnimalType.DOMESTIC, Skin.HAIR, Gender.MALE, Breed.BURMESE,
                new BigDecimal("65.00"), 1));

        this.addPetInventoryItem(new Dog(AnimalType.DOMESTIC, Skin.HAIR, Gender.MALE, Breed.GERMAN_SHEPARD,
                new BigDecimal("50.00"), 2));

        this.addPetInventoryItem(new Cat(AnimalType.DOMESTIC, Skin.UNKNOWN, Gender.FEMALE, Breed.SPHYNX,
                new BigDecimal("100.00"), 2));
    }

    public void initAddDuplicateItem(Pet addPet)
    {
        this.init();
        this.addPetInventoryItem(addPet);
    }

    public void printInventory()
    {
        Consumer<Pet> action = System.out::println;
        List<Pet> sortedPets = this.petsForSale.stream()
                .sorted(Comparator.comparing(Pet::getPetType))
                .collect(Collectors.toList());
        sortedPets.forEach(action);
    }

    public Pet soldPetItem(Pet soldPet)
            throws DuplicatePetStoreRecordException, PetNotFoundSaleException
    {
        if (soldPet.getPetStoreId() == 0) {
            throw new PetNotFoundSaleException("The Pet is not part of the pet store!!");
        }

        List<Pet> matchedPets = this.petsForSale.stream()
                .filter(p -> p.getPetStoreId() == soldPet.getPetStoreId()
                        && p.getPetType() == soldPet.getPetType())
                .collect(Collectors.toList());

        if (matchedPets.isEmpty()) {
            throw new PetNotFoundSaleException("Pet not found in store.");
        }

        if (matchedPets.size() > 1) {

            if (soldPet instanceof Dog) {
                throw new DuplicatePetStoreRecordException(
                        "Duplicate Dog record store id [" + soldPet.getPetStoreId() + "]");
            }

            if (soldPet instanceof Cat) {
                throw new DuplicatePetStoreRecordException(
                        "Duplicate Cat record store id [" + soldPet.getPetStoreId() + "]");
            }

            throw new DuplicatePetStoreRecordException(
                    "Duplicate Bird record store id [" + soldPet.getPetStoreId() + "]");
        }

        Pet foundPet = matchedPets.get(0);
        this.petsForSale.remove(foundPet);
        this.petsSold.add(foundPet);
        return foundPet;
    }


    public void addPetInventoryItem(Pet pet)
    {
        if (pet.getPetType() == PetType.BIRD) {

            boolean birdDuplicate = this.petsForSale.stream()
                    .anyMatch(p -> p.getPetType() == PetType.BIRD &&
                            p.getPetStoreId() == pet.getPetStoreId());

            if (birdDuplicate) {
                throw new DuplicatePetStoreRecordException(
                        "Duplicate Bird record store id [" + pet.getPetStoreId() + "]");
            }
        }

        this.petsForSale.add(pet);
    }

    public List<Pet> getPetsForSale()
    {
        return petsForSale;
    }
}