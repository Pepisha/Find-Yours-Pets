package company.pepisha.find_yours_pets.db.associations;


import company.pepisha.find_yours_pets.db.animal.AnimalConstants;
import company.pepisha.find_yours_pets.db.user.UserConstants;

public class FollowAnimalConstants {
    protected static final String FOLLOW_ANIMAL = "FollowAnimal";

    protected static final String ID_FOLLOW_ANIMAL = "idFollowAnimal";
    protected static final String ID_USER = "idUser";
    protected static final String ID_ANIMAL = "idAnimal";

    public static final String FOLLOW_ANIMAL_CREATE =  "create table " + FOLLOW_ANIMAL
            + "(" + ID_FOLLOW_ANIMAL + " integer primary key autoincrement, " +
            ID_USER + " integer not null, " +
            ID_ANIMAL + " integer not null, " +
            "FOREIGN KEY(" + ID_USER + ") REFERENCES " + UserConstants.USER
            + " (" + UserConstants.ID_USER + ") " +
            "FOREIGN KEY(" + ID_ANIMAL + ") REFERENCES " + AnimalConstants.ANIMAL
            + " (" + AnimalConstants.ID_ANIMAL + ") " +
            "); ";

    public static final String FOLLOW_ANIMAL_DROP = "DROP TABLE IF EXISTS " + FOLLOW_ANIMAL + "; ";
}
