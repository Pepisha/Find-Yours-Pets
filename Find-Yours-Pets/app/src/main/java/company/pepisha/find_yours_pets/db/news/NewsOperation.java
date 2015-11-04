package company.pepisha.find_yours_pets.db.news;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import company.pepisha.find_yours_pets.db.DataBaseWrapper;

public class NewsOperation {

    private DataBaseWrapper dbHelper;
    private String[] NEWS_TABLE_COLUMNS = { NewsConstants.ID_NEWS,
            NewsConstants.DESCRIPTION, NewsConstants.ID_ANIMAL };
    private SQLiteDatabase database;


    public NewsOperation(Context context){
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public News addNews(String description, int idAnimal) {
        ContentValues values = new ContentValues();

        values.put(NewsConstants.DESCRIPTION, description);
        values.put(NewsConstants.ID_ANIMAL, idAnimal);

        long newsId = database.insert(NewsConstants.NEWS, null, values);

        News news = getNews(newsId);
        return news;
    }

    public void deleteNews(News news) {
        long id = news.getIdNews();
        database.delete(NewsConstants.NEWS, NewsConstants.ID_NEWS
                + " = " + id, null);
    }

    public List getAllNews() {
        List newsList = new ArrayList();

        Cursor cursor = database.query(NewsConstants.NEWS,
                NEWS_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            News news = parseNews(cursor);
            newsList.add(news);
            cursor.moveToNext();
        }

        cursor.close();
        return newsList;
    }

    public News getNews(long newsId){
        Cursor cursor = database.query(NewsConstants.NEWS,
                NEWS_TABLE_COLUMNS, NewsConstants.ID_NEWS + " = "
                        + newsId, null, null, null, null);

        cursor.moveToFirst();

        News news = parseNews(cursor);
        cursor.close();
        return news;
    }

    private News parseNews(Cursor cursor) {
        News news = new News();
        news.setIdNews(cursor.getInt(0));
        news.setDescription(cursor.getString(1));
        news.setIdAnimal(cursor.getInt(2));

        return news;
    }
}
