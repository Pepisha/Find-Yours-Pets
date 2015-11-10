package company.pepisha.find_yours_pets.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import company.pepisha.find_yours_pets.db.address.AddressConstants;
import company.pepisha.find_yours_pets.db.animal.AnimalConstants;
import company.pepisha.find_yours_pets.db.animal.AnimalStateConstants;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalTypeConstants;
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

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AnimalTypeConstants.ANIMAL_TYPE_CREATE);
        db.execSQL(AnimalTypeConstants.ANIMAL_TYPE_DOG);
        db.execSQL(AnimalTypeConstants.ANIMAL_TYPE_CAT);
        db.execSQL(AnimalTypeConstants.ANIMAL_TYPE_NAC);
        db.execSQL(AnimalStateConstants.ANIMAL_STATE_CREATE);
        db.execSQL(AnimalStateConstants.ANIMAL_STATE_ADOPTION);
        db.execSQL(AnimalStateConstants.ANIMAL_STATE_ADOPTED);
        db.execSQL(AnimalConstants.ANIMAL_CREATE);
        db.execSQL(SubjectTypeConstants.SUBJECT_TYPE_CREATE);
        db.execSQL(SubjectTypeConstants.SUBJECT_TYPE_SHELTER);
        db.execSQL(SubjectTypeConstants.SUBJECT_TYPE_ANIMAL);
        db.execSQL(SubjectTypeConstants.SUBJECT_TYPE_USER);
        db.execSQL(PhotoConstants.PHOTO_CREATE);
        db.execSQL(UserConstants.USER_CREATE);
        db.execSQL(AddressConstants.ADDRESS_CREATE);
        db.execSQL(ShelterConstants.SHELTER_CREATE);
        db.execSQL(FollowAnimalConstants.FOLLOW_ANIMAL_CREATE);
        db.execSQL(AdoptConstants.ADOPT_CREATE);
        db.execSQL(ManagesConstants.MANAGES_CREATE);
        db.execSQL(IsAdminConstants.IS_ADMIN_CREATE);
        db.execSQL(FollowShelterConstants.FOLLOW_SHELTER_CREATE);
        db.execSQL(AnimalShelterConstants.ANIMAL_SHELTER_CREATE);
        db.execSQL(OpinionConstants.OPINION_CREATE);
        db.execSQL(NewsConstants.NEWS_CREATE);
        db.execSQL(PhotoNewsConstants.PHOTO_NEWS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AnimalTypeConstants.ANIMAL_TYPE_DROP);
        db.execSQL(AnimalStateConstants.ANIMAL_STATE_DROP);
        db.execSQL(AnimalConstants.ANIMAL_DROP);
        db.execSQL(SubjectTypeConstants.SUBJECT_TYPE_DROP);
        db.execSQL(PhotoConstants.PHOTO_DROP);
        db.execSQL(UserConstants.USER_DROP);
        db.execSQL(AddressConstants.ADDRESS_DROP);
        db.execSQL(ShelterConstants.SHELTER_DROP);
        db.execSQL(FollowAnimalConstants.FOLLOW_ANIMAL_DROP);
        db.execSQL(AdoptConstants.ADOPT_DROP);
        db.execSQL(ManagesConstants.MANAGES_DROP);
        db.execSQL(IsAdminConstants.IS_ADMIN_DROP);
        db.execSQL(FollowShelterConstants.FOLLOW_SHELTER_DROP);
        db.execSQL(AnimalShelterConstants.ANIMAL_SHELTER_DROP);
        db.execSQL(OpinionConstants.OPINION_DROP);
        db.execSQL(NewsConstants.NEWS_DROP);
        db.execSQL(PhotoNewsConstants.PHOTO_NEWS_DROP);

        onCreate(db);
    }
}
