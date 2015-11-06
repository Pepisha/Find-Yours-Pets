package company.pepisha.find_yours_pets.db.animal;


public class AnimalStateConstants {

    protected static final String ANIMAL_STATE = "AnimalState";

    protected static final String ID_ANIMAL_STATE = "idAnimalState";
    protected static final String NAME = "name";

    protected static final String ADOPTION = "Adoption";
    protected static final String ADOPTED = "Adopted";

    protected static final String ANIMAL_STATE_ADOPTION = "INSET INTO " + ANIMAL_STATE +
            "( " + ID_ANIMAL_STATE + ", " + NAME + " ) VALUES (1, " + ADOPTION + ") ;";

    protected static final String ANIMAL_STATE_ADOPTED = "INSET INTO " + ANIMAL_STATE +
            "( " + ID_ANIMAL_STATE + ", " + NAME + " ) VALUES (2, " + ADOPTED + ") ;";

    public static final String ANIMAL_STATE_CREATE =  "create table " + ANIMAL_STATE
            + "(" + ID_ANIMAL_STATE + " integer primary key autoincrement, " +
            NAME + " text not null "  +
            "); " +
            ANIMAL_STATE_ADOPTION +
            ANIMAL_STATE_ADOPTED;

    public static final String ANIMAL_STATE_DROP = "DROP TABLE IF EXISTS " + ANIMAL_STATE + "; ";
}
