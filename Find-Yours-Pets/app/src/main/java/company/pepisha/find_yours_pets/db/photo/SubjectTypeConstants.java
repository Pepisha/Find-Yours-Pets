package company.pepisha.find_yours_pets.db.photo;


public class SubjectTypeConstants {
    protected static final String SUBJECT_TYPE = "SubjectType";

    protected static final String ID_SUBJECT_TYPE = "idSubjectType";
    protected static final String NAME = "name";

    protected static final String SUBJECT_TYPE_CREATE = "create table " + SUBJECT_TYPE
            + "(" + ID_SUBJECT_TYPE + " integer primary key autoincrement, " +
            NAME + " text not null "  +
            ");";

    protected static final String SUBJECT_TYPE_DROP = "DROP TABLE IF EXIST " + SUBJECT_TYPE;
}
