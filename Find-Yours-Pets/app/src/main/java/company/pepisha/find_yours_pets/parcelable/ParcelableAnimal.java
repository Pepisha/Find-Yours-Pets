package company.pepisha.find_yours_pets.parcelable;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import company.pepisha.find_yours_pets.db.animal.Animal;

public class ParcelableAnimal extends Animal implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getIdAnimal());
        out.writeInt(getType());
        out.writeString(getName());
        out.writeString(getBreed());
        out.writeString(getAge());
        out.writeInt((getGender() == Gender.MALE) ? 0 : 1);
        out.writeString(getCatsFriend());
        out.writeString(getDogsFriend());
        out.writeString(getChildrenFriend());
        out.writeString(getDescription());
        out.writeInt(getState());
        out.writeString(getPhoto());
    }

    public static final Parcelable.Creator<ParcelableAnimal> CREATOR = new Parcelable.Creator<ParcelableAnimal>() {
        public ParcelableAnimal createFromParcel(Parcel in) {
            return new ParcelableAnimal(in);
        }

        public ParcelableAnimal[] newArray(int size) {
            return new ParcelableAnimal[size];
        }
    };

    private ParcelableAnimal(Parcel in) {
        setIdAnimal(in.readInt());
        setType(in.readInt());
        setName(in.readString());
        setBreed(in.readString());
        setAge(in.readString());

        int gender = in.readInt();
        setGender((gender == 0) ? Gender.MALE : Gender.FEMALE);
        setCatsFriend(in.readString());
        setDogsFriend(in.readString());
        setChildrenFriend(in.readString());
        setDescription(in.readString());
        setState(in.readInt());
        setPhoto(in.readString());
    }

    public ParcelableAnimal(JSONObject obj) {
        super(obj);
    }
}
