package company.pepisha.find_yours_pets.db.animal;


import company.pepisha.find_yours_pets.db.animal.animalType.AnimalTypeConstants;

public class AnimalConstants {

    public static final String ANIMAL = "Animal";

    public static final String ID_ANIMAL = "idAnimal";
    protected static final String ID_TYPE = "idType";
    protected static final String NAME = "name";
    protected static final String BREED = "breed";
    protected static final String AGE = "age";
    protected static final String GENDER = "gender";
    protected static final String CATS_FRIEND = "catsFriend";
    protected static final String DOGS_FRIEND = "dogsFriend";
    protected static final String CHILDREN_FRIEND = "childrenFriend";
    protected static final String DESCRIPTION = "description";
    protected static final String STATE = "idState";

    public static final String MALE = "male";
    public static final String FEMALE = "female";

    public static final String ANIMAL_CREATE = "create table " + ANIMAL
            + "(" + ID_ANIMAL + " integer primary key autoincrement, " +
            ID_TYPE + " integer not null, " +
                    NAME + " text not null, " +
                    BREED + " text not null, " +
                    AGE + " text not null, " +
                    GENDER + " text not null, " +
                    CATS_FRIEND + " text, " +
                    DOGS_FRIEND + " text, " +
                    CHILDREN_FRIEND + " text, " +
                    DESCRIPTION + " text, " +
                    STATE + " integer not null, " +
                    "FOREIGN KEY(" + ID_TYPE + ") REFERENCES " + AnimalTypeConstants.ANIMAL_TYPE
                        + " (" + AnimalTypeConstants.ID_ANIMAL_TYPE + ") " +
            ")";

    public static final String ANIMAL_DROP = "DROP TABLE IF EXISTS " + ANIMAL;


}
