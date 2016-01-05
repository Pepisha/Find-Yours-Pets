package company.pepisha.find_yours_pets.db.shelter;


import org.json.JSONException;
import org.json.JSONObject;

public class Shelter {
    private int idShelter;
    private String name;
    private String phone;
    private int idAddress;
    private String description;
    private String mail;
    private String website;
    private String operationalHours;
    private double stars;
    private boolean followed;


    public Shelter() {

    }

    public Shelter(JSONObject obj) {
        try {
            idShelter = obj.getInt("idShelter");
            name = obj.getString("name");
            phone = obj.getString("phone");
            idAddress = obj.getInt("idAddress");
            description = obj.getString("description");
            mail = obj.getString("mail");
            website = (obj.getString("website") != "null") ? obj.getString("website") : null;
            operationalHours = obj.getString("operationalHours");
            stars = obj.getDouble("average");
            followed = obj.getBoolean("followed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public String getOperationalHours() {
        return operationalHours;
    }

    public void setOperationalHours(String operationalHours) {
        this.operationalHours = operationalHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWebsite() { return website; }

    public void setWebsite(String website) { this.website = website; }

    public int getIdShelter() {
        return idShelter;
    }

    public void setIdShelter(int idShelter) {
        this.idShelter = idShelter;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public String toString() {
        return getName();
    }
}
