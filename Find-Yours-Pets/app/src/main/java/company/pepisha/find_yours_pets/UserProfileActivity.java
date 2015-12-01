package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.user.User;

public class UserProfileActivity  extends BaseActivity {

    private User user;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        HashMap<String,String> request = new HashMap<>();
        String nicknameUser = session.getUserDetails().get("nickname");

        request.put("nickname", nicknameUser);
        new GetUserInformationsDbOperation(getApplicationContext()).execute(request);

        oncClickExecuteUpdateProfileActivity();
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

    private void onClickExecuteUpdatePictureActivity() {
        Button update = (Button) findViewById(R.id.pictureButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(update);
            }
        });
    }
}
