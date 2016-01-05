package company.pepisha.find_yours_pets.db.animal;


import org.json.JSONException;
import org.json.JSONObject;

import company.pepisha.find_yours_pets.R;

public class Animal {
    public enum Gender {
        MALE, FEMALE
    }

    public static final int ADOPTION = 1;
    public static final int ADOPTED = 2;

    private int idAnimal;
    private int idType;
    private String name;
    private String breed;
    private String age;
    private Gender gender;
    private String catsFriend;
    private String dogsFriend;
    private String childrenFriend;
    private String description;
    private int idState;
    private String photo;
    private boolean followed;
    private boolean favorite;

    private int idShelter;

    public Animal() {

    }

    public Animal(JSONObject obj) {
        try {
            idAnimal = obj.getInt("idAnimal");
            idType = obj.getInt("idType");
            name = obj.getString("name");
            breed = obj.getString("breed");
            age = obj.getString("age");
            gender = (obj.getString("gender") == AnimalConstants.MALE) ? Gender.MALE : Gender.FEMALE;
            catsFriend = obj.getString("catsFriend");
            dogsFriend = obj.getString("dogsFriend");
            childrenFriend = obj.getString("childrenFriend");
            description = obj.getString("description");
            idState = obj.getInt("idState");
            photo = obj.getString("photo");
            followed = obj.getBoolean("followed");
            idShelter = obj.getInt("idShelter");
            favorite = obj.getBoolean("favorite");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getType() {
        return idType;
    }

    public void setType(int type) {
        this.idType = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) { this.gender = gender; }

    public String getCatsFriend() {
        return catsFriend;
    }

    public void setCatsFriend(String catsFriend) {
        this.catsFriend = catsFriend;
    }

    public String getDogsFriend() {
        return dogsFriend;
    }

    public void setDogsFriend(String dogsFriend) {
        this.dogsFriend = dogsFriend;
    }

    public String getChildrenFriend() {
        return childrenFriend;
    }

    public void setChildrenFriend(String childrenFriend) {
        this.childrenFriend = childrenFriend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return idState;
    }

    public void setState(int state) {
        this.idState = state;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getIdShelter() {
        return idShelter;
    }

    public void setIdShelter(int idShelter) {
        this.idShelter = idShelter;
    }

    public int getDefaultImage() {
        if (getType() == 1) {
            return R.drawable.dog;
        } else if (getType() == 2) {
            return R.drawable.cat;
        } else if (getType() == 3) {
            return R.drawable.nac;
        }

        return 0;
    }
}
