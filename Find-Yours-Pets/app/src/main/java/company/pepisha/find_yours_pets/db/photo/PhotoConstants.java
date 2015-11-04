package company.pepisha.find_yours_pets.db.photo;


import company.pepisha.find_yours_pets.db.adress.AddressConstants;

public class PhotoConstants {
    protected static final String PHOTO = "Photo";

    protected static final String ID_PHOTO = "idPhoto";
    protected static final String NAME = "name";
    protected static final String DESCRIPTION = "description";
    protected static final String ID_SUBJECT = "idSubject";
    protected static final String ID_SUBJECT_TYPE = "idSubjectType";

    protected static final String PHOTO_CREATE = "create table " + PHOTO
            + "(" + ID_PHOTO + " integer primary key autoincrement, " +
            NAME + " integer not null, " +
            DESCRIPTION + " text not null, " +
            ID_SUBJECT + " integer not null, " +
            ID_SUBJECT_TYPE + " integer not null, " +
            "FOREIGN KEY(" + ID_SUBJECT_TYPE + ") REFERENCES " + SubjectTypeConstants.SUBJECT_TYPE
            + "(" + SubjectTypeConstants.ID_SUBJECT_TYPE + ")" +
            ");";

    protected static final String PHOTO_DROP = "DROP TABLE IF NOT EXISTS "+PHOTO;
}
