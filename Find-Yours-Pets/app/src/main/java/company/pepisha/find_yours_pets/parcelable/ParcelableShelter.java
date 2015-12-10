package company.pepisha.find_yours_pets.parcelable;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import company.pepisha.find_yours_pets.db.shelter.Shelter;

public class ParcelableShelter extends Shelter implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getIdShelter());
        out.writeString(getName());
        out.writeString(getPhone());
        out.writeInt(getIdAddress());
        out.writeString(getDescription());
        out.writeString(getMail());
        out.writeString(getWebsite());
        out.writeString(getOperationalHours());
        out.writeInt(getIdFacebook());
        out.writeInt(getIdTwitter());
        out.writeInt(getIdInstagram());
    }

    public static final Parcelable.Creator<ParcelableShelter> CREATOR = new Parcelable.Creator<ParcelableShelter>() {
        public ParcelableShelter createFromParcel(Parcel in) {
            return new ParcelableShelter(in);
        }

        public ParcelableShelter[] newArray(int size) {
            return new ParcelableShelter[size];
        }
    };

    private ParcelableShelter(Parcel in) {
        setIdShelter(in.readInt());
        setName(in.readString());
        setPhone(in.readString());
        setIdAddress(in.readInt());
        setDescription(in.readString());
        setMail(in.readString());
        setWebsite(in.readString());
        setOperationalHours(in.readString());
        setIdFacebook(in.readInt());
        setIdTwitter(in.readInt());
        setIdInstagram(in.readInt());
    }

    public ParcelableShelter(JSONObject obj) {
        super(obj);
    }
}
