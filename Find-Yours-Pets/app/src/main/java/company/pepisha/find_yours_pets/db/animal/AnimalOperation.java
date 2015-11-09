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
    private String[] ANIMAL_TABLE_COLUMNS = { AnimalConstants.ID_ANIMAL, AnimalConstants.ID_TYPE,
            AnimalConstants.NAME, AnimalConstants.BREED, AnimalConstants.AGE, AnimalConstants.GENDER,
            AnimalConstants.CATS_FRIEND, AnimalConstants.DOGS_FRIEND, AnimalConstants.CHILDREN_FRIEND,
            AnimalConstants.DESCRIPTION, AnimalConstants.STATE };

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

    public Animal addAnimal(String type, String name, String breed, String age, String gender, String catsFriend,
                                String dogsFriend, String childrenFriend, String description, String state){
        ContentValues values = new ContentValues();

        values.put(AnimalConstants.ID_TYPE, type);
        values.put(AnimalConstants.NAME, name);
        values.put(AnimalConstants.BREED, breed);
        values.put(AnimalConstants.AGE, age);
        values.put(AnimalConstants.GENDER, gender);
        values.put(AnimalConstants.CATS_FRIEND, catsFriend);
        values.put(AnimalConstants.DOGS_FRIEND, dogsFriend);
        values.put(AnimalConstants.CHILDREN_FRIEND, childrenFriend);
        values.put(AnimalConstants.DESCRIPTION, description);
        values.put(AnimalConstants.STATE, state);

        long animalId = database.insert(AnimalConstants.ANIMAL, null, values);

        Animal animal = getAnimal(animalId);
        return animal;
    }

    public void deleteAnimal(Animal animal) {
        long id = animal.getIdAnimal();
        database.delete(AnimalConstants.ANIMAL, AnimalConstants.ID_ANIMAL
                + " = " + id, null);
    }

    public List getAllAnimals() {
        List animals = new ArrayList();

        Cursor cursor = database.query(AnimalConstants.ANIMAL,
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
        Cursor cursor = database.query(AnimalConstants.ANIMAL,
                ANIMAL_TABLE_COLUMNS, AnimalConstants.ID_ANIMAL + " = "
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
        animal.setGender((cursor.getString(5) == "Male") ? Animal.Gender.MALE : Animal.Gender.FEMALE);
        animal.setCatsFriend(cursor.getString(6));
        animal.setDogsFriend(cursor.getString(7));
        animal.setChildrenFriend(cursor.getString(8));
        animal.setDescription(cursor.getString(9));
        animal.setState(cursor.getInt(10));

        return animal;
    }
}
