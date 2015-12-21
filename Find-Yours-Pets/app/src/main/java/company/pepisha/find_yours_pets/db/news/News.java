package company.pepisha.find_yours_pets.db.news;


import org.json.JSONException;
import org.json.JSONObject;


public class News {
    private int idNews;
    private String description;
    private int idAnimal;
    private String dateNews;

    public News() {

    }

    public News(JSONObject obj) {
        try {
            idNews = obj.getInt("idNews");
            idAnimal = obj.getInt("idAnimal");
            description = obj.getString("description");
            dateNews = obj.getString("dateNews");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getDateNews() {
        return dateNews;
    }

    public void setDateNews(String dateNews) {
        this.dateNews = dateNews;
    }

    public int getIdNews() {
        return idNews;
    }

    public void setIdNews(int idNews) {
        this.idNews = idNews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    @Override
    public String toString() {
        return description + "[ le " + dateNews + ']';
    }
}
