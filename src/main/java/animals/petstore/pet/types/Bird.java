package animals.petstore.pet.types;

import animals.AnimalType;
import animals.petstore.pet.Pet;
import animals.petstore.pet.attributes.Breed;
import animals.petstore.pet.attributes.Gender;
import animals.petstore.pet.attributes.PetType;
import animals.petstore.pet.attributes.Skin;

import java.math.BigDecimal;

public class Bird extends Pet implements PetImpl {

    /* Properties */
    private int numberOfWings;
    private Breed breed;

    /**
     * Constructor
     */
    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed)
    {
        this(animalType, skinType, gender, breed, new BigDecimal(0));
    }

    /**
     * Constructor
     */
    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost)
    {
        this(animalType, skinType, gender, breed, cost, 0);
    }

    /**
     * Constructor
     */
    public Bird(AnimalType animalType, Skin skinType, Gender gender, Breed breed, BigDecimal cost, int petStoreId)
    {
        super(PetType.BIRD, cost, gender, petStoreId);
        super.skinType = skinType;
        super.animalType = animalType;
        this.numberOfWings = 2;
        this.breed = breed;
    }

    /**
     * Is the bird allergy friendly determined by skin type
     */
    public String birdHypoallergenic()
    {
        return super.petHypoallergenic(this.skinType).replaceAll("pet", "bird");
    }

    /**
     * What does the bird say
     */
    public String speak()
    {
        String language;
        switch(animalType){
            case DOMESTIC:
                language = "The bird goes chirp! chirp!";
                break;
            case WILD:
                language = "The bird goes screech! screech!";
                break;
            default:
                language = "The bird goes " + super.getPetType().speak + "! " + super.getPetType().speak + "!";
        }
        return language;
    }

    private String numberOfWings()
    {
        return "Birds have " + numberOfWings + " wings!";
    }

    public int getNumberOfWings() {
        return numberOfWings;
    }

    public void setNumberOfWings(int numberOfWings) {
        this.numberOfWings = numberOfWings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Breed getBreed() {
        return this.breed;
    }

    public AnimalType getAnimalType() {
        return this.animalType;
    }

    @Override
    public String toString()
    {
        return super.toString() +
                "The bird is " + super.animalType + "!\n" +
                "The birds breed is " + this.getBreed() + "!\n" +
                this.birdHypoallergenic() + "!\n" +
                this.speak() + "\n" +
                this.numberOfWings();
    }
}
