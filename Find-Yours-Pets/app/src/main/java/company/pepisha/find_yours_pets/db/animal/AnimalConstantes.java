package company.pepisha.find_yours_pets.db.animal;


public class AnimalConstantes {

    protected static final String ANIMAL = "Animal";

    protected static final String ID_ANIMAL = "idAnimal";
    protected static final String TYPE = "idType";
    protected static final String NAME = "name";
    protected static final String BREED = "breed";
    protected static final String AGE = "age";
    protected static final String CATS_FRIEND = "catsFriend";
    protected static final String DOGS_FRIEND = "dogsFriend";
    protected static final String CHILDREN_FRIEND = "childrenFriend";
    protected static final String DESCRIPTION = "description";
    protected static final String STATE = "idState";


    protected static final String ANIMAL_CREATE = "create table " + ANIMAL
            + "(" + ID_ANIMAL + " integer primary key autoincrement, " +
                    TYPE + " integer not null, " +
                    NAME + " text not null, " +
                    BREED + " text not null, " +
                    AGE + " text not null, " +
                    CATS_FRIEND + " text, " +
                    DOGS_FRIEND + " text, " +
                    CHILDREN_FRIEND + " text, " +
                    DESCRIPTION + " text, " +
                    STATE + " integer not null" +
            ");";

    protected static final String ANIMAL_DROP = "DROP TABLE IF EXISTS " + ANIMAL;

}
