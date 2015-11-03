package company.pepisha.find_yours_pets.db.animal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;

public class AnimalOperation {

    private DataBaseWrapper dbHelper;
    private String[] ANIMAL_TABLE_COLUMNS = { AnimalConstantes.ID_ANIMAL, AnimalConstantes.TYPE,
            AnimalConstantes.NAME, AnimalConstantes.BREED, AnimalConstantes.AGE, AnimalConstantes.CATS_FRIEND,
            AnimalConstantes.DOGS_FRIEND, AnimalConstantes.CHILDREN_FRIEND, AnimalConstantes.DESCRIPTION,
            AnimalConstantes.STATE };
    private SQLiteDatabase database;


    public AnimalOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Animal addAnimal(String type, String name, String breed, String age, String catsFriend,
                                String dogsFriend, String childrenFriend, String description, String state){
        ContentValues values = new ContentValues();

        values.put(AnimalConstantes.TYPE, type);
        values.put(AnimalConstantes.NAME, name);
        values.put(AnimalConstantes.BREED, breed);
        values.put(AnimalConstantes.AGE, age);
        values.put(AnimalConstantes.CATS_FRIEND, catsFriend);
        values.put(AnimalConstantes.DOGS_FRIEND, dogsFriend);
        values.put(AnimalConstantes.CHILDREN_FRIEND, childrenFriend);
        values.put(AnimalConstantes.DESCRIPTION, description);
        values.put(AnimalConstantes.STATE, state);

        long animalId = database.insert(AnimalConstantes.ANIMAL, null, values);

        Animal animal = getAnimal(animalId);
        return animal;
    }

    public void deleteAnimal(Animal animal) {
        long id = animal.getIdAnimal();
        database.delete(AnimalConstantes.ANIMAL, AnimalConstantes.ID_ANIMAL
                + " = " + id, null);
    }

    public List getAllAnimals() {
        List animals = new ArrayList();

        Cursor cursor = database.query(AnimalConstantes.ANIMAL,
                ANIMAL_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Animal animal = parseAnimal(cursor);
            animals.add(animal);
            cursor.moveToNext();
        }

        cursor.close();
        return animals;
    }

    public Animal getAnimal(long animalId){
        Cursor cursor = database.query(AnimalConstantes.ANIMAL,
                ANIMAL_TABLE_COLUMNS, AnimalConstantes.ID_ANIMAL + " = "
                        + animalId, null, null, null, null);

        cursor.moveToFirst();

        Animal animal = parseAnimal(cursor);
        cursor.close();
        return animal;
    }

    private Animal parseAnimal(Cursor cursor) {
        Animal animal = new Animal();
        animal.setIdAnimal(cursor.getInt(0));
        animal.setType(cursor.getInt(1));
        animal.setName(cursor.getString(2));
        animal.setBreed(cursor.getString(3));
        animal.setAge(cursor.getString(4));
        animal.setCatsFriend(cursor.getString(5));
        animal.setDogsFriend(cursor.getString(6));
        animal.setChildrenFriend(cursor.getString(7));
        animal.setDescription(cursor.getString(8));
        animal.setState(cursor.getInt(9));

        return animal;
    }
}
