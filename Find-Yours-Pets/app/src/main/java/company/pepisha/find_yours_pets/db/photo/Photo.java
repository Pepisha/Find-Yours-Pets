package company.pepisha.find_yours_pets.db.photo;


public class Photo {
    private int idPhoto;
    private String name;
    private String description;
    private int idSubject;
    private int idSubjectType;

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String photoName) {
        this.name = photoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public int getIdSubjectType() {
        return idSubjectType;
    }

    public void setIdSubjectType(int idSubjectType) {
        this.idSubjectType = idSubjectType;
    }
}
