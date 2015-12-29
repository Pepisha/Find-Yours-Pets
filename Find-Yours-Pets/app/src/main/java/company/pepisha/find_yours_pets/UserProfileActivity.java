package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.user.User;
import company.pepisha.find_yours_pets.views.AnimalViews;

public class UserProfileActivity  extends BaseActivity {

    private User user;

    private GridLayout petsGrid;

    private Map<Integer, Animal> animalsList = new HashMap<>();

    private class GetUserInformationsDbOperation extends ServerDbOperation {
        public GetUserInformationsDbOperation(Context c) {
            super(c, "getUserInformations");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            user = new User((JSONObject)result.get(session.getUserDetails().get("nickname")));
            fillUserFields();
        }
    }

    private class GetUsersAnimalsDbOperation extends ServerDbOperation {
        public GetUsersAnimalsDbOperation(Context c) {
            super(c, "getUsersAnimals");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null) {
                addAnimals(result);
            }
        }
    }

    private void fillUserFields() {
        TextView userNickname = (TextView) findViewById(R.id.userLogin);
        userNickname.setText(user.getNickname());

        TextView userFirstname = (TextView) findViewById(R.id.userFirstname);
        userFirstname.setText(user.getFirstname());

        TextView userLastname = (TextView) findViewById(R.id.userLastname);
        userLastname.setText(user.getLastname());

        TextView userMail = (TextView) findViewById(R.id.userMail);
        userMail.setText(user.getEmail());

        TextView userPhone = (TextView) findViewById(R.id.userPhone);
        userPhone.setText(user.getPhone());
    }

    private void addAnimals(HashMap<String, Object> animals) {
        animalsList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(petsGrid, animalsList, session);
    }

    private void oncClickExecuteUpdateProfileActivity() {
        Button update = (Button) findViewById(R.id.updtateButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(getApplicationContext(), UpdateUserProfileActivity.class);
                startActivity(update);
            }
        });
    }

    private void onClickCallUser() {
        final ImageButton callButton = (ImageButton) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + user.getPhone()));
                startActivity(callIntent);
            }
        });
    }

    private void getUsersAnimals() {
        String nicknameUser = session.getUserDetails().get("nickname");
        HashMap<String, String> animalsRequest = new HashMap<String, String>();
        animalsRequest.put("nickname", nicknameUser);
        new GetUsersAnimalsDbOperation(this).execute(animalsRequest);
    }

    private void getUsersInformations() {
        HashMap<String,String> userRequest = new HashMap<>();
        String nicknameUser = session.getUserDetails().get("nickname");
        userRequest.put("nickname", nicknameUser);
        new GetUserInformationsDbOperation(getApplicationContext()).execute(userRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        petsGrid = (GridLayout) findViewById(R.id.petsGrid);

        getUsersInformations();
        getUsersAnimals();

        oncClickExecuteUpdateProfileActivity();
        onClickCallUser();
    }
}
