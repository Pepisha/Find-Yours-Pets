package company.pepisha.find_yours_pets.db.opinion;


import org.json.JSONException;
import org.json.JSONObject;

public class Opinion {
    private int idOpinion;
    private int stars;
    private String comment;
    private String date;
    private int idShelter;
    private int idUser;

    public Opinion() {

    }

    public Opinion(JSONObject obj) {
        try {
            idOpinion = obj.getInt("idOpinion");
            stars = obj.getInt("stars");
            comment = obj.getString("description");
            idShelter = obj.getInt("idShelter");
            idUser = obj.getInt("idUser");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIdOpinion() {
        return idOpinion;
    }

    public void setIdOpinion(int idOpinion) {
        this.idOpinion = idOpinion;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIdShelter() {
        return idShelter;
    }

    public void setIdShelter(int idShelter) {
        this.idShelter = idShelter;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String toString() {
        return getComment() + " [" + stars + " stars]";
    }
}
