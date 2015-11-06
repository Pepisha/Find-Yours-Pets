package company.pepisha.find_yours_pets.db.opinion;


import company.pepisha.find_yours_pets.db.shelter.ShelterConstants;
import company.pepisha.find_yours_pets.db.user.UserConstants;

public class OpinionConstants {
    protected static final String OPINION = "Opinion";

    protected static final String ID_OPINION = "idOpinion";
    protected static final String STARS = "stars";
    protected static final String COMMENT = "description";
    protected static final String ID_SHELTER = "idShelter";
    protected static final String ID_USER = "idUser";

    public static final String OPINION_CREATE = "create table " + OPINION
            + "(" + ID_OPINION + " integer primary key autoincrement, " +
            STARS + " integer not null, " +
            COMMENT + " text not null, " +
            ID_SHELTER + " integer not null, " +
            ID_USER + " integer not null, " +
            "FOREIGN KEY(" + ID_SHELTER + ") REFERENCES " + ShelterConstants.SHELTER
                            + "(" + ShelterConstants.ID_SHELTER + ")," +
            "FOREIGN KEY(" + ID_USER + ") REFERENCES " + UserConstants.USER
                            + "(" + UserConstants.ID_USER + ")," +
            "); ";

    public static final String OPINION_DROP = "DROP TABLE IF NOT EXISTS " + OPINION + "; ";
}
