package company.pepisha.find_yours_pets.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import company.pepisha.find_yours_pets.db.message.Message;

public class ParcelableMessage extends Message implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getIdMessage());
        out.writeInt(getIdAnimal());
        out.writeInt(getIdShelter());
        out.writeString(getContent());
        out.writeString(getDateMessage());
        out.writeByte((byte) (isMessageRead() ? 1 : 0));
        out.writeString(getNickname());
        out.writeString(getAnimalName());
    }

    public static final Parcelable.Creator<ParcelableMessage> CREATOR = new Parcelable.Creator<ParcelableMessage>() {
        public ParcelableMessage createFromParcel(Parcel in) {
            return new ParcelableMessage(in);
        }

        public ParcelableMessage[] newArray(int size) {
            return new ParcelableMessage[size];
        }
    };

    private ParcelableMessage(Parcel in) {
        setIdMessage(in.readInt());
        setIdAnimal(in.readInt());
        setIdShelter(in.readInt());
        setContent(in.readString());
        setDateMessage(in.readString());
        setMessageRead(in.readByte() != 0);
        setNickname(in.readString());
        setAnimalName(in.readString());
    }

    public ParcelableMessage(JSONObject obj) {
        super(obj);
    }
}
