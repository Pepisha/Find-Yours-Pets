package company.pepisha.find_yours_pets.db.shelter;


import company.pepisha.find_yours_pets.db.address.AddressConstants;

public class ShelterConstants {

    public static final String SHELTER = "Shelter";

    public static final String ID_SHELTER = "idShelter";
    protected static final String NAME = "name";
    protected static final String PHONE = "phone";
    protected static final String ID_ADDRESS = "idAddress";
    protected static final String DESCRIPTION = "description";
    protected static final String MAIL = "mail";
    protected static final String OPERATIONAL_HOURS = "operationalHours";


    public static final String SHELTER_CREATE = "create table " + SHELTER
            + "(" + ID_SHELTER + " integer primary key autoincrement, " +
            NAME + " text not null, " +
            PHONE + " text not null, " +
            ID_ADDRESS + " integer not null, " +
            DESCRIPTION + " text, " +
            MAIL + " text not null, " +
            OPERATIONAL_HOURS + " text, " +
            "FOREIGN KEY(" + ID_ADDRESS + ") REFERENCES " + AddressConstants.ADDRESS
                        + " (" + AddressConstants.ID_ADDRESS + ")" +
            ")";

    public static final String SHELTER_DROP = "DROP TABLE IF EXISTS " + SHELTER;
}
