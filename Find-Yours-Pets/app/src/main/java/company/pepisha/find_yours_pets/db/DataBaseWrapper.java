package company.pepisha.find_yours_pets.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseWrapper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pepisha.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "";
    private static final String DATABASE_DROP_IF_EXIST = "";

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP_IF_EXIST);
        onCreate(db);
    }
}
