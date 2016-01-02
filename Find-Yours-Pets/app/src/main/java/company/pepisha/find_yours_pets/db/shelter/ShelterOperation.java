package company.pepisha.find_yours_pets.db.shelter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;

public class ShelterOperation {

    private DataBaseWrapper dbHelper;
    private String[] SHELTER_TABLE_COLUMNS = { ShelterConstants.ID_SHELTER, ShelterConstants.NAME,
            ShelterConstants.PHONE, ShelterConstants.ID_ADDRESS, ShelterConstants.DESCRIPTION, ShelterConstants.MAIL,
            ShelterConstants.OPERATIONAL_HOURS };
    private SQLiteDatabase database;


    public ShelterOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Shelter addShelter(String name, String phone, int idAddress, String description,
                            String mail, String operationalHours){
        ContentValues values = new ContentValues();

        values.put(ShelterConstants.NAME, name);
        values.put(ShelterConstants.PHONE, phone);
        values.put(ShelterConstants.ID_ADDRESS, idAddress);
        values.put(ShelterConstants.DESCRIPTION, description);
        values.put(ShelterConstants.MAIL, mail);
        values.put(ShelterConstants.OPERATIONAL_HOURS, operationalHours);

        long shelterId = database.insert(ShelterConstants.SHELTER, null, values);

        Shelter shelter = getShelter(shelterId);
        return shelter;
    }

    public void deleteShelter(Shelter shelter) {
        long id = shelter.getIdShelter();
        database.delete(ShelterConstants.SHELTER, ShelterConstants.ID_SHELTER
                + " = " + id, null);
    }

    public List getAllShelters() {
        List shelters = new ArrayList();

        Cursor cursor = database.query(ShelterConstants.SHELTER,
                SHELTER_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Shelter shelter = parseShelter(cursor);
            shelters.add(shelter);
            cursor.moveToNext();
        }

        cursor.close();
        return shelters;
    }

    public Shelter getShelter(long shelterId){
        Cursor cursor = database.query(ShelterConstants.SHELTER,
                SHELTER_TABLE_COLUMNS, ShelterConstants.ID_SHELTER + " = "
                        + shelterId, null, null, null, null);

        cursor.moveToFirst();

        Shelter shelter = parseShelter(cursor);
        cursor.close();
        return shelter;
    }

    private Shelter parseShelter(Cursor cursor) {
        Shelter shelter = new Shelter();
        shelter.setIdShelter(cursor.getInt(0));
        shelter.setName(cursor.getString(1));
        shelter.setPhone(cursor.getString(2));
        shelter.setIdAddress(cursor.getInt(3));
        shelter.setDescription(cursor.getString(4));
        shelter.setMail(cursor.getString(5));
        shelter.setOperationalHours(cursor.getString(6));

        return shelter;
    }
}
