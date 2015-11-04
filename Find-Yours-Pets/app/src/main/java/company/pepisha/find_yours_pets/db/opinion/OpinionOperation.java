package company.pepisha.find_yours_pets.db.opinion;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;

public class OpinionOperation {

    private DataBaseWrapper dbHelper;
    private String[] OPINION_TABLE_COLUMNS = { OpinionConstants.ID_OPINION, OpinionConstants.STARS,
            OpinionConstants.COMMENT, OpinionConstants.ID_SHELTER, OpinionConstants.ID_USER };
    private SQLiteDatabase database;


    public OpinionOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Opinion addOpinion(int stars, String comment, int idShelter, int idUser) {
        ContentValues values = new ContentValues();

        values.put(OpinionConstants.STARS, stars);
        values.put(OpinionConstants.COMMENT, comment);
        values.put(OpinionConstants.ID_SHELTER, idShelter);
        values.put(OpinionConstants.ID_USER, idUser);

        long opinionId = database.insert(OpinionConstants.OPINION, null, values);

        Opinion opinion = getOpinion(opinionId);
        return opinion;
    }

    public void deleteOpinion(Opinion opinion) {
        long id = opinion.getIdOpinion();
        database.delete(OpinionConstants.OPINION, OpinionConstants.ID_OPINION
                + " = " + id, null);
    }

    public List getAllOpinions() {
        List opinions = new ArrayList();

        Cursor cursor = database.query(OpinionConstants.OPINION,
                OPINION_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Opinion opinion = parseOpinion(cursor);
            opinions.add(opinion);
            cursor.moveToNext();
        }

        cursor.close();
        return opinions;
    }

    public Opinion getOpinion(long opinionId){
        Cursor cursor = database.query(OpinionConstants.OPINION,
                OPINION_TABLE_COLUMNS, OpinionConstants.ID_OPINION + " = "
                        + opinionId, null, null, null, null);

        cursor.moveToFirst();

        Opinion opinion = parseOpinion(cursor);
        cursor.close();
        return opinion;
    }

    private Opinion parseOpinion(Cursor cursor) {
        Opinion opinion = new Opinion();
        opinion.setIdOpinion(cursor.getInt(0));
        opinion.setStars(cursor.getInt(1));
        opinion.setComment(cursor.getString(2));
        opinion.setIdShelter(cursor.getInt(3));
        opinion.setIdUser(cursor.getInt(4));

        return opinion;
    }
}
