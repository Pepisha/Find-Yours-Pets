package company.pepisha.find_yours_pets.db.message;


import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private int idMessage;
    private String content;
    private String dateMessage;
    private boolean messageRead;
    private String nickname;
    private int idShelter;
    private int idAnimal;
    private String animalName;

    public Message() {

    }

    public Message(JSONObject obj) {
        try {
            idMessage = obj.getInt("idMessage");
            content = obj.getString("content");
            dateMessage = obj.getString("dateMessage");
            messageRead = obj.getBoolean("messageRead");
            nickname = obj.getString("nickname");
            idShelter = obj.getInt("idShelter");
            idAnimal = obj.getInt("idAnimal");

            if (obj.has("animalName")) {
                animalName = obj.getString("animalName");
            }
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
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

    public boolean isMessageRead() {
        return messageRead;
    }

    public void setMessageRead(boolean messageRead) {
        this.messageRead = messageRead;
    }
}
