package company.pepisha.find_yours_pets.db.associations;

import company.pepisha.find_yours_pets.db.news.NewsConstants;
import company.pepisha.find_yours_pets.db.photo.PhotoConstants;

public class PhotoNewsConstants {
    protected static final String PHOTO_NEWS = "PhotoNews";

    protected static final String ID_PHOTO_NEWS = "idPhotoNews";
    protected static final String ID_PHOTO = "idPhoto";
    protected static final String ID_NEWS = "idNews";

    public static final String PHOTO_NEWS_CREATE =  "create table " + PHOTO_NEWS
            + "(" + ID_PHOTO_NEWS + " integer primary key autoincrement, " +
            ID_PHOTO + " integer not null, " +
            ID_NEWS + " integer not null, " +
            "FOREIGN KEY(" + ID_PHOTO + ") REFERENCES " + PhotoConstants.PHOTO
            + " (" + PhotoConstants.ID_PHOTO + "), " +
            "FOREIGN KEY(" + ID_NEWS + ") REFERENCES " + NewsConstants.NEWS
            + " (" + NewsConstants.ID_NEWS + ") " +
            ")";

    public static final String PHOTO_NEWS_DROP = "DROP TABLE IF EXISTS " + PHOTO_NEWS;

}
