package company.pepisha.find_yours_pets.db.animal.animalType;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalTypeConstants;

public class AnimalTypeOperation {

    private DataBaseWrapper dbHelper;
    private String[] ANIMAL_TYPE_COLUMNS = {AnimalTypeConstants.ID_ANIMAL_TYPE, AnimalTypeConstants.NAME};
    private SQLiteDatabase database;



    public AnimalTypeOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List getAllAnimalTypes(){
        List types = new ArrayList();
        Cursor cursor = database.query(AnimalTypeConstants.ANIMAL_TYPE, ANIMAL_TYPE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            AnimalType animalType = parseAnimalType(cursor);
            types.add(animalType);
            cursor.moveToNext();
        }
        return types;
    }

    public AnimalType getAnimalType(long idAnimalType){
        Cursor cursor = database.query(AnimalTypeConstants.ANIMAL_TYPE,
                ANIMAL_TYPE_COLUMNS, AnimalTypeConstants.ID_ANIMAL_TYPE + " = "
                        + idAnimalType, null, null, null, null);

        cursor.moveToFirst();

        AnimalType animalType = parseAnimalType(cursor);
        cursor.close();
        return animalType;
    }

    private AnimalType parseAnimalType(Cursor cursor) {

        AnimalType animalType = new AnimalType();
        animalType.setIdAnimalType(cursor.getInt(0));
        animalType.setName(cursor.getString(1));

        return animalType;
    }
}
