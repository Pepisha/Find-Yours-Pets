package company.pepisha.find_yours_pets.db.associations;


import company.pepisha.find_yours_pets.db.shelter.ShelterConstants;
import company.pepisha.find_yours_pets.db.user.UserConstants;

public class FollowShelterConstants {
    protected static final String FOLLOW_SHELTER = "FollowShelter";

    protected static final String ID_FOLLOW_SHELTER = "idFollowShelter";
    protected static final String ID_USER = "idUser";
    protected static final String ID_SHELTER = "idShelter";

    public static final String FOLLOW_SHELTER_CREATE =  "create table " + FOLLOW_SHELTER
            + "(" + ID_FOLLOW_SHELTER + " integer primary key autoincrement, " +
            ID_USER + " integer not null, " +
            ID_SHELTER + " integer not null, " +
            "FOREIGN KEY(" + ID_USER + ") REFERENCES " + UserConstants.USER
            + " (" + UserConstants.ID_USER + ") " +
            "FOREIGN KEY(" + ID_SHELTER + ") REFERENCES " + ShelterConstants.SHELTER
            + " (" + ShelterConstants.ID_SHELTER + ") " +
            "); ";

    public static final String FOLLOW_SHELTER_DROP = "DROP TABLE IF EXISTS " + FOLLOW_SHELTER +"; ";
}
