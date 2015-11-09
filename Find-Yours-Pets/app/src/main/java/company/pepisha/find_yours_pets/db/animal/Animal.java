package company.pepisha.find_yours_pets.db.animal;


public class Animal {
    public enum Gender {
        MALE, FEMALE
    }

    private int idAnimal;
    private int type;
    private String name;
    private String breed;
    private String age;
    private Gender gender;
    private String catsFriend;
    private String dogsFriend;
    private String childrenFriend;
    private String description;
    private int state;

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
