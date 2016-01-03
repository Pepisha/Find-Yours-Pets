package company.pepisha.find_yours_pets.db.message;


import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private int idMessage;
    private String content;
    private String dateMessage;
    private int idUser;
    private int idShelter;

    public Message() {

    }

    public Message(JSONObject obj) {
        try {
            idMessage = obj.getInt("idMessage");
            content = obj.getString("content");
            dateMessage = obj.getString("dateMessage");
            idUser = obj.getInt("idUser");
            idShelter = obj.getInt("idShelter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdShelter() {
        return idShelter;
    }

    public void setIdShelter(int idShelter) {
        this.idShelter = idShelter;
    }

    public String getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(String dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
