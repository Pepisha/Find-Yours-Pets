package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.facebook.share.widget.ShareDialog;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.AnimalConstants;
import company.pepisha.find_yours_pets.socialNetworksManagers.FacebookManager;
import company.pepisha.find_yours_pets.socialNetworksManagers.TwitterManager;
import company.pepisha.find_yours_pets.views.AnimalViews;

public class AddAnimalActivity extends BaseActivity {

    ShareDialog shareDialog;

    private class AddAnimalDbOperation extends ServerDbOperation {
        public AddAnimalDbOperation(Context c) {
            super(c, "addAnimalInShelter");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {

                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeScreen);

                shareOnSocialNetworks();
            }
        }
    }

    private void shareOnSocialNetworks() {
        EditText animalName = (EditText) findViewById(R.id.animalName);
        RadioGroup radioGroupAnimalTypes = (RadioGroup) findViewById(R.id.radioGroupAnimalTypes);
        RadioGroup radioGroupAnimalGender = (RadioGroup) findViewById(R.id.radioGroupAnimalSexe);
        EditText breedAnimal = (EditText) findViewById(R.id.breedAnimal);
        EditText ageAnimal = (EditText) findViewById(R.id.ageAnimal);
        RatingBar catsFriend = (RatingBar) findViewById(R.id.catsFriendRatingBar);
        RatingBar dogsAgreements = (RatingBar) findViewById(R.id.dogsFriendRatingBar);
        RatingBar childrenAgreements = (RatingBar) findViewById(R.id.childrenFriendRatingBar);
        EditText description = (EditText) findViewById(R.id.description);

        String postTitle = animalName.getText().toString();
        String postContent = animalName.getText().toString() + "\n"
                + breedAnimal.getText().toString() + "\n"
                + ageAnimal.getText().toString() + "\n"
                + Float.toString(catsFriend.getRating()) + "/5\n"
                + Float.toString(dogsAgreements.getRating()) + "/5\n"
                + Float.toString(childrenAgreements.getRating()) + "/5\n"
                + description.getText().toString();

        shareAnimalOnFacebook(postTitle, postContent);
        tweetAnimal(postContent);
    }

    private void animalAdding() {

        EditText animalName = (EditText) findViewById(R.id.animalName);
        RadioGroup radioGroupAnimalTypes = (RadioGroup) findViewById(R.id.radioGroupAnimalTypes);
        RadioGroup radioGroupAnimalGender = (RadioGroup) findViewById(R.id.radioGroupAnimalSexe);
        EditText breedAnimal = (EditText) findViewById(R.id.breedAnimal);
        EditText ageAnimal = (EditText) findViewById(R.id.ageAnimal);
        RatingBar catsFriend = (RatingBar) findViewById(R.id.catsFriendRatingBar);
        RatingBar dogsAgreements = (RatingBar) findViewById(R.id.dogsFriendRatingBar);
        RatingBar childrenAgreements = (RatingBar) findViewById(R.id.childrenFriendRatingBar);
        EditText description = (EditText) findViewById(R.id.description);
        int idShelter = getIntent().getIntExtra("idShelter",1);
        RadioButton checkedAnimalGender = (RadioButton) findViewById(radioGroupAnimalGender.getCheckedRadioButtonId());

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("name", animalName.getText().toString());
        request.put("type", Integer.toString(radioGroupAnimalTypes.getCheckedRadioButtonId()));
        request.put("gender", (checkedAnimalGender.getText().toString().equals(getResources().getString(R.string.male)))
                                            ? AnimalConstants.MALE : AnimalConstants.FEMALE);
        request.put("breed", breedAnimal.getText().toString());
        request.put("age", ageAnimal.getText().toString());
        request.put("catsFriend", Float.toString(catsFriend.getRating()));
        request.put("dogsFriend", Float.toString(dogsAgreements.getRating()));
        request.put("childrenFriend", Float.toString(childrenAgreements.getRating()));
        request.put("description", description.getText().toString());
        request.put("idShelter", Integer.toString(idShelter));

        new AddAnimalDbOperation(getApplicationContext()).execute(request);
    }

    private void tweetAnimal(String postContent) {
        CheckBox tweet = (CheckBox) findViewById(R.id.checkBoxTwitter);
        if(tweet.isChecked()) {
            TwitterManager.tweetWithoutImage(postContent, this);
        }
    }

    private void shareAnimalOnFacebook(String postTitle, String postContent) {
        CheckBox facebookShare = (CheckBox) findViewById(R.id.checkBoxFacebook);
        if(facebookShare.isChecked()) {
            shareDialog = new ShareDialog(this);
            shareDialog.show(FacebookManager.share(postTitle, postContent, ServerConnectionManager.url));

            //TODO ajouter image quand l'upload d'images marchera
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupAnimalTypes);
        AnimalViews.createTypesRadioButtons(radioGroup);

        Button createButton = (Button) findViewById(R.id.addAnimalButton);

        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                animalAdding();
            }
        });
    }
}
