package company.pepisha.find_yours_pets.db.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;
import company.pepisha.find_yours_pets.db.animal.Animal;

public class UserOperation {

    private DataBaseWrapper dbHelper;
    private String[] USER_TABLE_COLUMNS = {UserConstants.ID_USER, UserConstants.NICKNAME,
        UserConstants.PASSWORD, UserConstants.EMAIL, UserConstants.PHONE, UserConstants.FIRSTNAME,
        UserConstants.LASTNAME};
    private SQLiteDatabase database;

    public UserOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws android.database.SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public User addUser(String nickname, String password, String email, String phone,
                        String firstname, String lastname){

        if (!userExist(nickname)) {

            ContentValues values = new ContentValues();

            values.put(UserConstants.NICKNAME, nickname);
            values.put(UserConstants.PASSWORD, password);
            values.put(UserConstants.EMAIL, email);
            values.put(UserConstants.PHONE, phone);
            values.put(UserConstants.FIRSTNAME, firstname);
            values.put(UserConstants.LASTNAME, lastname);

            long userId = database.insert(UserConstants.USER, null, values);

            User user = getUser(userId);
            return user;
        }

        return null;
    }

    public void deleteUser(User user){
        long idUser = user.getIdUser();
        database.delete(UserConstants.USER, UserConstants.ID_USER
                + " = " + idUser, null);
    }

    public List getAllUsers(){
        List users = new ArrayList();

        Cursor cursor = database.query(UserConstants.USER,
                USER_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            User user = parseUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }

        cursor.close();
        return users;
    }

    public User getUser(long userId){
        Cursor cursor = database.query(UserConstants.USER,
                USER_TABLE_COLUMNS, UserConstants.ID_USER + " = "
                        + userId, null, null, null, null);

        cursor.moveToFirst();

        User user = parseUser(cursor);
        cursor.close();
        return user;
    }

    public boolean userExist(String nickname) {
        Cursor cursor = database.query(UserConstants.USER,
                USER_TABLE_COLUMNS, UserConstants.NICKNAME + " = "
                        + nickname, null, null, null, null);

        return (cursor != null && cursor.getCount() > 0);
    }

    public boolean userConnection(String nickname, String password) {
        Cursor cursor = database.query(UserConstants.USER,
                USER_TABLE_COLUMNS,
                UserConstants.NICKNAME + " = " + nickname + " AND " + UserConstants.PASSWORD + " = " + password,
                null, null, null, null);

        return (cursor != null && cursor.getCount() > 0);
    }

    private User parseUser(Cursor cursor){
        User user = new User();
        user.setIdUser(cursor.getInt(0));
        user.setNickname(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setEmail(cursor.getString(3));
        user.setPhone(cursor.getString(4));
        user.setFirstname(cursor.getString(5));
        user.setNickname(cursor.getString(6));

        return user;
    }
}
