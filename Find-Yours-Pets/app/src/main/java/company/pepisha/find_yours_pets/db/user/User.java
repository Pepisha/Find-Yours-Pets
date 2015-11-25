package company.pepisha.find_yours_pets.db.user;


import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int idUser;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;

    public User(JSONObject obj) {
        try {
            nickname = obj.getString("nickname");
            email = obj.getString("mail");
            phone = obj.getString("phone");
            firstname = obj.getString("firstname");
            lastname = obj.getString("lastname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
