package company.pepisha.find_yours_pets.db.animal;

public class AnimalTypeConstants {

    protected static final String ANIMAL_TYPE = "AnimalType";

    protected static final String ID_ANIMAL_TYPE = "idAnimalType";
    protected static final String NAME = "name";

    protected static final String DOG = "Dog";
    protected static final String CAT = "Cat";
    protected static final String NAC = "NAC";

    protected static final String ANIMAL_TYPE_CHIEN = "INSERT INTO " + ANIMAL_TYPE +
        "( " + ID_ANIMAL_TYPE + ", " + NAME + ") VALUES (1, " + DOG + "); ";

    protected static final String ANIMAL_TYPE_CHAT = "INSERT INTO " + ANIMAL_TYPE +
            "( " + ID_ANIMAL_TYPE + ", " + NAME + ") VALUES (2, " + CAT + "); ";

    protected static final String ANIMAL_TYPE_NAC = "INSERT INTO " + ANIMAL_TYPE +
            "( " + ID_ANIMAL_TYPE + ", " + NAME + ") VALUES (3, " + NAC + "); ";


    public static final String ANIMAL_TYPE_CREATE = "create table " + ANIMAL_TYPE
            + "(" + ID_ANIMAL_TYPE + " integer primary key autoincrement, " +
            NAME + " text not null "  +
            "); " +
            ANIMAL_TYPE_CHIEN +
            ANIMAL_TYPE_CHAT +
            ANIMAL_TYPE_NAC;

    public static final String ANIMAL_TYPE_DROP = "DROP TABLE IF EXISTS " + ANIMAL_TYPE + "; ";
}
