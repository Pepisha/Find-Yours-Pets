package company.pepisha.find_yours_pets.db.adress;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;

public class AddressOperation {

    private DataBaseWrapper dbHelper;
    private String[] ADRESS_TABLE_COLUMNS = {AddressConstants.ID_ADDRESS, AddressConstants.STREET,
            AddressConstants.ZIPCODE, AddressConstants.CITY, AddressConstants.LATITUDE,
            AddressConstants.LONGITUDE };
    private SQLiteDatabase database;

    public AddressOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Address addAddress(String street, String zipcode, String city, String latitude, String longitude){
        ContentValues values = new ContentValues();

        values.put(AddressConstants.STREET, street);
        values.put(AddressConstants.ZIPCODE, zipcode);
        values.put(AddressConstants.CITY, city);
        values.put(AddressConstants.LATITUDE, latitude);
        values.put(AddressConstants.LONGITUDE, longitude);

        long addressId = database.insert(AddressConstants.ADDRESS, null, values);

        Address address = getAddress(addressId);
        return address;
    }

    public void deleteAddress(Address address){
        long id = address.getIdAddress();
        database.delete(AddressConstants.ADDRESS, AddressConstants.ID_ADDRESS + " = " + id, null);
    }

    public List getAllAdsress(){
        List address = new ArrayList();
        Cursor cursor = database.query(AddressConstants.ADDRESS, ADRESS_TABLE_COLUMNS,
                            null, null, null, null, null );
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Address a = parseAddress(cursor);
            address.add(a);
            cursor.moveToFirst();
        }
        cursor.close();
        return address;
    }

    public Address getAddress(long addressId){
        Cursor cursor = database.query(AddressConstants.ADDRESS, ADRESS_TABLE_COLUMNS,
                AddressConstants.ID_ADDRESS + " = " + addressId, null, null, null, null, null);
        cursor.moveToFirst();

        Address address = parseAddress(cursor);
        return address;
    }

    private Address parseAddress(Cursor cursor){
        Address address = new Address();
        address.setIdAddress(cursor.getInt(0));
        address.setStreet(cursor.getString(1));
        address.setZipcode(cursor.getString(2));
        address.setCity(cursor.getString(3));
        address.setLatitude(cursor.getString(4));
        address.setLongitude(cursor.getString(5));

        return address;
    }

}