package company.pepisha.find_yours_pets.db.photo;


import company.pepisha.find_yours_pets.db.animal.AnimalConstants;
import company.pepisha.find_yours_pets.db.shelter.ShelterConstants;
import company.pepisha.find_yours_pets.db.user.UserConstants;

public class SubjectTypeConstants {
    protected static final String SUBJECT_TYPE = "SubjectType";

    protected static final String ID_SUBJECT_TYPE = "idSubjectType";
    protected static final String NAME = "name";

    protected static final String SUBJECT_TYPE_SHELTER = "INSERT INTO " + SUBJECT_TYPE +" " +
            "(" + ID_SUBJECT_TYPE + ", " + NAME + ") VALUES (1, " + ShelterConstants.SHELTER + " ); ";

    protected static final String SUBJECT_TYPE_ANIMAL =  "INSERT INTO " + SUBJECT_TYPE +" " +
            "(" + ID_SUBJECT_TYPE + ", " + NAME + ") VALUES (2, " + AnimalConstants.ANIMAL + " ); ";

    protected static final String SUBJECT_TYPE_USER =  "INSERT INTO " + SUBJECT_TYPE +" " +
            "(" + ID_SUBJECT_TYPE + ", " + NAME + ") VALUES (3, " + UserConstants.USER + " ); ";


    public static final String SUBJECT_TYPE_CREATE = "create table " + SUBJECT_TYPE
            + "(" + ID_SUBJECT_TYPE + " integer primary key autoincrement, " +
            NAME + " text not null "  +
            "); " +
            SUBJECT_TYPE_SHELTER +
            SUBJECT_TYPE_ANIMAL +
            SUBJECT_TYPE_USER ;


    public static final String SUBJECT_TYPE_DROP = "DROP TABLE IF EXIST " + SUBJECT_TYPE + "; ";
}

