package company.pepisha.find_yours_pets.db.user;


public class UserConstants {
    public static final String USER = "User";

    public static final String ID_USER = "idUser";
    protected static final String NICKNAME = "nickname";
    protected static final String PASSWORD = "password";
    protected static final String EMAIL = "email";
    protected static final String PHONE = "phone";
    protected static final String FIRSTNAME =  "firstname";
    protected static final String LASTNAME = "lastname";

    public static final String USER_CREATE = "create table " + USER
            + "(" + ID_USER + " integer primary key autoincrement, " +
            NICKNAME + " text not null, " +
            PASSWORD + " text not null, " +
            EMAIL + " text not null, " +
            PHONE + " text not null, " +
            FIRSTNAME + " text not null, " +
            LASTNAME + " text not null " +
            ")";

    public static final String USER_DROP = "DROP TABLE IF EXISTS " + USER;
}
