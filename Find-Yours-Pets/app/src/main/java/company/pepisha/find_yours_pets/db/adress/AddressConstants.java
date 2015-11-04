package company.pepisha.find_yours_pets.db.adress;


public class AddressConstants {

    public static final String ADDRESS = "Address";

    public static final String ID_ADDRESS = "idAddress";
    protected static final String STREET = "street";
    protected static final String ZIPCODE = "zipcode";
    protected static final String CITY = "city";
    protected static final String LATITUDE = "latitude";
    protected static final String LONGITUDE = "longitude";

    protected static final String ADDRESS_CREATE =  "create table " + ADDRESS
            + "(" + ID_ADDRESS + " integer primary key autoincrement, " +
            STREET + " integer not null, " +
            ZIPCODE + " text not null, " +
            CITY + " text not null, " +
            LATITUDE + " text not null, " +
            LONGITUDE + " text not null " +
            ");";

    protected static final String ADDRESS_DROP = "DROP TABLE IF EXISTS " + ADDRESS;
}
