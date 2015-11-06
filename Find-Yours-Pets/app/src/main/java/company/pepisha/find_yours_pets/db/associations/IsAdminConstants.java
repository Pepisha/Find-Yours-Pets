package company.pepisha.find_yours_pets.db.associations;


import company.pepisha.find_yours_pets.db.shelter.ShelterConstants;
import company.pepisha.find_yours_pets.db.user.UserConstants;

public class IsAdminConstants {
    protected static final String IS_ADMIN = "IsAdmin";

    protected static final String ID_IS_ADMIN = "idIsAdmin";
    protected static final String ID_USER = "idUser";
    protected static final String ID_SHELTER = "idShelter";

    public static final String IS_ADMIN_CREATE =  "create table " + IS_ADMIN
            + "(" + ID_IS_ADMIN + " integer primary key autoincrement, " +
            ID_USER + " integer not null, " +
            ID_SHELTER + " integer not null, " +
            "FOREIGN KEY(" + ID_USER + ") REFERENCES " + UserConstants.USER
            + " (" + UserConstants.ID_USER + ") " +
            "FOREIGN KEY(" + ID_SHELTER + ") REFERENCES " + ShelterConstants.SHELTER
            + " (" + ShelterConstants.ID_SHELTER + ") " +
            "); ";

    public static final String IS_ADMIN_DROP = "DROP TABLE IF EXISTS " + IS_ADMIN + "; ";
}
