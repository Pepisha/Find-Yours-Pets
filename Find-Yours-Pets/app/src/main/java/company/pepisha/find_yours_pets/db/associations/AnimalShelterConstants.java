package company.pepisha.find_yours_pets.db.associations;


import company.pepisha.find_yours_pets.db.animal.AnimalConstants;
import company.pepisha.find_yours_pets.db.shelter.ShelterConstants;

public class AnimalShelterConstants {
    protected static final String ANIMAL_SHELTER = "AnimalShelter";

    protected static final String ID_ANIMAL_SHELTER = "idAnimalShelter";
    protected static final String ID_ANIMAL = "idAnimal";
    protected static final String ID_SHELTER = "idShelter";

    public static final String ANIMAL_SHELTER_CREATE =  "create table " + ANIMAL_SHELTER
            + "(" + ID_ANIMAL_SHELTER + " integer primary key autoincrement, " +
            ID_ANIMAL + " integer not null, " +
            ID_SHELTER + " integer not null, " +
            "FOREIGN KEY(" + ID_SHELTER + ") REFERENCES " + ShelterConstants.SHELTER
            + " (" + ShelterConstants.ID_SHELTER + ") " +
            "FOREIGN KEY(" + ID_ANIMAL + ") REFERENCES " + AnimalConstants.ANIMAL
            + " (" + AnimalConstants.ID_ANIMAL + ") " +
            "); ";

    public static final String ANIMAL_SHELTER_DROP = "DROP TABLE IF EXISTS " + ANIMAL_SHELTER + "; ";
}
