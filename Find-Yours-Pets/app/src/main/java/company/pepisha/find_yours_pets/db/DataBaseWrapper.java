package company.pepisha.find_yours_pets.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import company.pepisha.find_yours_pets.db.address.AddressConstants;
import company.pepisha.find_yours_pets.db.animal.AnimalConstants;
import company.pepisha.find_yours_pets.db.animal.AnimalStateConstants;
import company.pepisha.find_yours_pets.db.animal.AnimalTypeConstants;
import company.pepisha.find_yours_pets.db.associations.AdoptConstants;
import company.pepisha.find_yours_pets.db.associations.AnimalShelterConstants;
import company.pepisha.find_yours_pets.db.associations.FollowAnimalConstants;
import company.pepisha.find_yours_pets.db.associations.FollowShelterConstants;
import company.pepisha.find_yours_pets.db.associations.IsAdminConstants;
import company.pepisha.find_yours_pets.db.associations.ManagesConstants;
import company.pepisha.find_yours_pets.db.associations.PhotoNewsConstants;
import company.pepisha.find_yours_pets.db.news.NewsConstants;
import company.pepisha.find_yours_pets.db.opinion.OpinionConstants;
import company.pepisha.find_yours_pets.db.photo.PhotoConstants;
import company.pepisha.find_yours_pets.db.photo.SubjectTypeConstants;
import company.pepisha.find_yours_pets.db.shelter.ShelterConstants;
import company.pepisha.find_yours_pets.db.user.UserConstants;

public class DataBaseWrapper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pepisha.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = AnimalTypeConstants.ANIMAL_TYPE_CREATE + AnimalStateConstants.ANIMAL_STATE_CREATE
            + AnimalConstants.ANIMAL_CREATE + SubjectTypeConstants.SUBJECT_TYPE_CREATE + PhotoConstants.PHOTO_CREATE
            + UserConstants.USER_CREATE + AddressConstants.ADDRESS_CREATE + ShelterConstants.SHELTER_CREATE
            + FollowAnimalConstants.FOLLOW_ANIMAL_CREATE + AdoptConstants.ADOPT_CREATE + ManagesConstants.MANAGES_CREATE
            + IsAdminConstants.IS_ADMIN_CREATE + FollowShelterConstants.FOLLOW_SHELTER_CREATE + AnimalShelterConstants.ANIMAL_SHELTER_CREATE
            + OpinionConstants.OPINION_CREATE + NewsConstants.NEWS_CREATE + PhotoNewsConstants.PHOTO_NEWS_CREATE;

    private static final String DATABASE_DROP_IF_EXIST = PhotoNewsConstants.PHOTO_NEWS_DROP + NewsConstants.NEWS_DROP + OpinionConstants.OPINION_DROP
            + AnimalShelterConstants.ANIMAL_SHELTER_DROP + FollowShelterConstants.FOLLOW_SHELTER_DROP + IsAdminConstants.IS_ADMIN_DROP + ManagesConstants.MANAGES_DROP
            + AdoptConstants.ADOPT_DROP + FollowAnimalConstants.FOLLOW_ANIMAL_DROP + ShelterConstants.SHELTER_DROP + AddressConstants.ADDRESS_DROP
            + UserConstants.USER_DROP + PhotoConstants.PHOTO_DROP + SubjectTypeConstants.SUBJECT_TYPE_DROP + AnimalConstants.ANIMAL_DROP
            + AnimalStateConstants.ANIMAL_STATE_DROP + AnimalTypeConstants.ANIMAL_TYPE_DROP;

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP_IF_EXIST);
        onCreate(db);
    }
}
