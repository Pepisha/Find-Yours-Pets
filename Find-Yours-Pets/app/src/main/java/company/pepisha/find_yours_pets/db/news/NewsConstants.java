package company.pepisha.find_yours_pets.db.news;


import company.pepisha.find_yours_pets.db.animal.AnimalConstants;

public class NewsConstants {
    public static final String NEWS = "News";

    public static final String ID_NEWS = "idNews";
    protected static final String DESCRIPTION = "description";
    protected static final String ID_ANIMAL = "idAnimal";

    public static final String NEWS_CREATE = "create table " + NEWS
            + "(" + ID_NEWS + " integer primary key autoincrement, " +
            DESCRIPTION + " text not null, " +
            ID_ANIMAL + " integer not null, " +
            "FOREIGN KEY(" + ID_ANIMAL + ") REFERENCES " + AnimalConstants.ANIMAL
            + "(" + AnimalConstants.ID_ANIMAL + ")," +
            "); ";

    public static final String NEWS_DROP = "DROP TABLE IF NOT EXISTS " + NEWS + "; ";
}
