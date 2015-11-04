package company.pepisha.find_yours_pets.db.shelter;


public class ShelterConstants {

    protected static final String SHELTER = "Shelter";

    protected static final String ID_SHELTER = "idShelter";
    protected static final String NAME = "name";
    protected static final String PHONE = "phone";
    protected static final String ID_ADDRESS = "idAddress";
    protected static final String DESCRIPTION = "description";
    protected static final String MAIL = "mail";
    protected static final String OPERATIONAL_HOURS = "operationalHours";
    protected static final String ID_FACEBOOK = "idFacebook";
    protected static final String ID_TWITTER = "idTwitter";
    protected static final String ID_INSTAGRAM = "idInstagram";


    protected static final String SHELTER_CREATE = "create table " + SHELTER
            + "(" + ID_SHELTER + " integer primary key autoincrement, " +
            NAME + " text not null, " +
            PHONE + " text not null, " +
            ID_ADDRESS + " integer not null, " +
            DESCRIPTION + " text, " +
            MAIL + " text not null, " +
            OPERATIONAL_HOURS + " text, " +
            ID_FACEBOOK + " text not null" +
            ID_TWITTER + " text not null" +
            ID_INSTAGRAM + " text not null" +
            "FOREIGN KEY(" + ID_ADDRESS + ") REFERENCES " + AddressConstants.ADDRESS
                        + "(" + AddressConstants.ID_ADDRESS + ")" +
            ");";

    protected static final String SHELTER_DROP = "DROP TABLE IF EXISTS " + SHELTER;
}
