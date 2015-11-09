package company.pepisha.find_yours_pets.db.associations;


import company.pepisha.find_yours_pets.db.animal.AnimalConstants;

public class AdoptConstants {
    protected static final String ADOPT = "Adopt";

    protected static final String ID_ADOPT = "idAdopt";
    protected static final String ID_ANIMAL = "idAnimal";
    protected static final String ID_USER = "idUser";

    public static final String ADOPT_CREATE = "create table " + ADOPT
            + "(" + ID_ADOPT + " integer primary key autoincrement, " +
            ID_ANIMAL + " integer not null, " +
            ID_USER + " integer not null, " +

            "FOREIGN KEY(" + ID_ANIMAL + ") REFERENCES " + AnimalConstants.ANIMAL
            + " (" + AnimalConstants.ID_ANIMAL + ") " +
            ")";

    public static final String ADOPT_DROP = "DROP TABLE IF EXISTS " + ADOPT;
}
