package company.pepisha.find_yours_pets;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;

public class PetsPreferencesActivity extends BaseActivity {

    private Switch catsSwitch;
    private Switch dogsSwitch;
    private Switch childrenSwitch;

    private RatingBar catsRatingBar;
    private RatingBar dogsRatingBar;
    private RatingBar childrenRatingBar;

    private class GetUserPetsPreferencesDbOperation extends ServerDbOperation {

        public GetUserPetsPreferencesDbOperation(Context c) {
            super(c, "getUserPetsPreferences");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null) {
                setPreferences(result);
            }
        }
    }

    private class SetUserPetsPreferencesDbOperation extends ServerDbOperation {

        public SetUserPetsPreferencesDbOperation(Context c) {
            super(c, "setUserPetsPreferences");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.preferencesModified), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void onSwitchClick() {

        catsSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                catsRatingBar.setEnabled(catsSwitch.isChecked());
            }
        });

        dogsSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dogsRatingBar.setEnabled(dogsSwitch.isChecked());
            }
        });

        childrenSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                childrenRatingBar.setEnabled(childrenSwitch.isChecked());
            }
        });
    }

    private void setPreferences(HashMap<String, Object> result) {
        catsSwitch.setChecked(result.containsKey("catsFriend"));
        dogsSwitch.setChecked(result.containsKey("dogsFriend"));
        childrenSwitch.setChecked(result.containsKey("childrenFriend"));

        if (result.containsKey("catsFriend")) {
            catsRatingBar.setRating(Float.parseFloat(result.get("catsFriend").toString()));
        }

        if (result.containsKey("dogsFriend")) {
            dogsRatingBar.setRating(Float.parseFloat(result.get("dogsFriend").toString()));
        }

        if (result.containsKey("childrenFriend")) {
            childrenRatingBar.setRating(Float.parseFloat(result.get("childrenFriend").toString()));
        }
    }

    private void onSaveButtonClick() {
        Button saveButton = (Button) findViewById(R.id.savePreferencesButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> request = new HashMap<String, String>();
                request.put("nickname", session.getUserDetails().get("nickname"));

                if (catsSwitch.isChecked()) {
                    request.put("catsFriend", Float.toString(catsRatingBar.getRating()));
                }

                if (dogsSwitch.isChecked()) {
                    request.put("dogsFriend", Float.toString(dogsRatingBar.getRating()));
                }

                if (childrenSwitch.isChecked()) {
                    request.put("childrenFriend", Float.toString(childrenRatingBar.getRating()));
                }

                new SetUserPetsPreferencesDbOperation(getApplicationContext()).execute(request);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_preferences);

        catsSwitch = (Switch) findViewById(R.id.catsSwitch);
        dogsSwitch = (Switch) findViewById(R.id.dogsSwitch);
        childrenSwitch = (Switch) findViewById(R.id.childrenSwitch);

        catsRatingBar = (RatingBar) findViewById(R.id.catsPrefRatingBar);
        dogsRatingBar = (RatingBar) findViewById(R.id.dogsPrefRatingBar);
        childrenRatingBar = (RatingBar) findViewById(R.id.childrenPrefRatingBar);

        onSwitchClick();
        onSaveButtonClick();

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("nickname", session.getUserDetails().get("nickname"));
        new GetUserPetsPreferencesDbOperation(this).execute(request);
    }
}
