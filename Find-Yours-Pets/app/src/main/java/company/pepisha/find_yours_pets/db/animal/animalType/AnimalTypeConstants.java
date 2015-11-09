package company.pepisha.find_yours_pets.db.animal.animalType;

public class AnimalTypeConstants {

    public static final String ANIMAL_TYPE = "AnimalType";

    public static final String ID_ANIMAL_TYPE = "idAnimalType";
    protected static final String NAME = "name";

    protected static final String DOG = "Dog";
    protected static final String CAT = "Cat";
    protected static final String NAC = "NAC";

    public static final String ANIMAL_TYPE_DOG = "INSERT INTO " + ANIMAL_TYPE +
            "(" + NAME + ") VALUES ('" + DOG + "')";

    public static final String ANIMAL_TYPE_CAT = "INSERT INTO " + ANIMAL_TYPE +
            "(" + NAME + ") VALUES ('" + CAT + "')";

    public static final String ANIMAL_TYPE_NAC = "INSERT INTO " + ANIMAL_TYPE +
            "(" + NAME + ") VALUES ('" + NAC + "')";


    public static final String ANIMAL_TYPE_CREATE = "create table " + ANIMAL_TYPE
            + "(" + ID_ANIMAL_TYPE + " integer primary key autoincrement, " +
            NAME + " text not null "  +
            ")";

    public static final String ANIMAL_TYPE_DROP = "DROP TABLE IF EXISTS " + ANIMAL_TYPE;
}
