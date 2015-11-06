package company.pepisha.find_yours_pets.db.associations;

import company.pepisha.find_yours_pets.db.shelter.ShelterConstants;
import company.pepisha.find_yours_pets.db.user.UserConstants;

public class ManagesConstants {
    protected static final String MANAGES = "Manages";

    protected static final String ID_MANAGES = "idManages";
    protected static final String ID_USER = "idUser";
    protected static final String ID_SHELTER = "idShelter";

    public static final String MANAGES_CREATE =  "create table " + MANAGES
            + "(" + ID_MANAGES + " integer primary key autoincrement, " +
            ID_USER + " integer not null, " +
            ID_SHELTER + " integer not null, " +
            "FOREIGN KEY(" + ID_USER + ") REFERENCES " + UserConstants.USER
            + " (" + UserConstants.ID_USER + ") " +
            "FOREIGN KEY(" + ID_SHELTER + ") REFERENCES " + ShelterConstants.SHELTER
            + " (" + ShelterConstants.ID_SHELTER + ") " +
            "); ";

    public static final String MANAGES_DROP = "DROP TABLE IF EXISTS " + MANAGES + "; ";
}
