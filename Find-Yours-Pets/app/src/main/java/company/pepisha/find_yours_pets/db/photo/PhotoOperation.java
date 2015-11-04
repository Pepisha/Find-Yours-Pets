package company.pepisha.find_yours_pets.db.photo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;

public class PhotoOperation {

    private DataBaseWrapper dbHelper;
    private String[] PHOTO_TABLE_COLUMNS = {PhotoConstants.ID_PHOTO,
            PhotoConstants.NAME, PhotoConstants.DESCRIPTION, PhotoConstants.ID_SUBJECT,
            PhotoConstants.ID_SUBJECT_TYPE};
    private SQLiteDatabase database;

    public PhotoOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Photo addPhoto(String name, String description, int idSubject, int idSubjectType){
        ContentValues values = new ContentValues();

        values.put(PhotoConstants.NAME, name);
        values.put(PhotoConstants.DESCRIPTION, description);
        values.put(PhotoConstants.ID_SUBJECT, idSubject);
        values.put(PhotoConstants.ID_SUBJECT_TYPE, idSubjectType);

        long photoId = database.insert(PhotoConstants.PHOTO, null, values);

        Photo photo = getPhoto(photoId);
        return photo;
    }

    public void deletePhoto(Photo photo){
        long id = photo.getIdPhoto();
        database.delete(PhotoConstants.PHOTO, PhotoConstants.ID_PHOTO + " = " + id, null);
    }

    public List getAllPhotos(){
        List photos = new ArrayList();
        Cursor cursor = database.query(PhotoConstants.PHOTO, PHOTO_TABLE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Photo photo = parsePhoto(cursor);
            photos.add(photo);
            cursor.moveToNext();
        }
        return photos;
    }

    public Photo getPhoto(long idPhoto){
        Cursor cursor = database.query(PhotoConstants.PHOTO, PHOTO_TABLE_COLUMNS,
                PhotoConstants.ID_PHOTO + " = " + idPhoto, null, null, null, null);
        cursor.moveToFirst();
        Photo photo = parsePhoto(cursor);
        cursor.close();
        return photo;
    }

    private Photo parsePhoto(Cursor cursor){
        Photo photo = new Photo();

        photo.setIdPhoto(cursor.getInt(0));
        photo.setName(cursor.getString(1));
        photo.setDescription(cursor.getString(2));
        photo.setIdSubject(cursor.getInt(3));
        photo.setIdSubjectType(cursor.getInt(4));

        return photo;
    }

}
