package company.pepisha.find_yours_pets.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import company.pepisha.find_yours_pets.db.user.User;

public class ParcelableUser extends User implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getIdUser());
        out.writeString(getNickname());
        out.writeString(getFirstname());
        out.writeString(getLastname());
        out.writeString(getEmail());
        out.writeString(getPhone());
    }

    public static final Parcelable.Creator<ParcelableUser> CREATOR = new Parcelable.Creator<ParcelableUser>() {
        public ParcelableUser createFromParcel(Parcel in) { return new ParcelableUser(in); }

        public ParcelableUser[] newArray(int size) { return new ParcelableUser[size]; }
    };

    private ParcelableUser(Parcel in) {
        setIdUser(in.readInt());
        setNickname(in.readString());
        setFirstname(in.readString());
        setLastname(in.readString());
        setEmail(in.readString());
        setPhone(in.readString());
    }

    public ParcelableUser(JSONObject obj) { super(obj); }
}
