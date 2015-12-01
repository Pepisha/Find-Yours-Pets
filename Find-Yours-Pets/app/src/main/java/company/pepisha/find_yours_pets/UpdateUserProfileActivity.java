package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.user.User;

public class UpdateUserProfileActivity extends BaseActivity {

    private User user;
    private String nicknameUser;

    private class GetUserInformationsDbOperation extends ServerDbOperation {
        public GetUserInformationsDbOperation(Context c) {
            super(c, "getUserInformations");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            user = new User((JSONObject)result.get(nicknameUser));
            fillUserFields();
        }
    }

    private class UpdateUserInformationsDbOperation extends ServerDbOperation {
        public UpdateUserInformationsDbOperation(Context c) {
            super(c, "updateUserProfile");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if(successResponse(result)) {
                Intent userProfile = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(userProfile);
            } else {
                Toast.makeText(getApplicationContext(), "failed profile update : "+result.get("error"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fillUserFields() {
        TextView userNickname = (TextView) findViewById(R.id.userNickname);
        userNickname.setText(nicknameUser);

        if(user.getFirstname() != null) {
            EditText userNewFirstname = (EditText) findViewById(R.id.userNewFirstname);
            userNewFirstname.setText(user.getFirstname());
        }

        if(user.getLastname() != null) {
            EditText userNewLastname = (EditText) findViewById(R.id.userNewLastname);
            userNewLastname.setText(user.getLastname());
        }

        if(user.getEmail() != null) {
            EditText userNewEmail = (EditText) findViewById(R.id.userNewMail);
            userNewEmail.setText(user.getEmail());
        }

        if(user.getPhone() != null) {
            EditText userNewPhone = (EditText) findViewById(R.id.userNewPhone);
            userNewPhone.setText(user.getPhone());
        }
    }

    private void askInformationsAboutUser() {
        HashMap<String,String> request = new HashMap<>();
        request.put("nickname", nicknameUser);
        new GetUserInformationsDbOperation(getApplicationContext()).execute(request);
    }

    private void updateUserInformations() {
        EditText newFirstname = (EditText) findViewById(R.id.userNewFirstname);
        EditText newLastname = (EditText) findViewById(R.id.userNewLastname);
        EditText newEmail = (EditText) findViewById(R.id.userNewMail);
        EditText newPhone = (EditText) findViewById(R.id.userNewPhone);
        EditText newPassword = (EditText) findViewById(R.id.userNewPassword);
        EditText confirmNewPassword = (EditText) findViewById(R.id.userConfirmNewPassword);

        HashMap<String, String> request = new HashMap<>();
        request.put("nickname", nicknameUser);
        request.put("newFirstname", newFirstname.getText().toString());
        request.put("newLastname", newLastname.getText().toString());
        request.put("newMail", newEmail.getText().toString());
        request.put("newPhone", newPhone.getText().toString());
        request.put("newPassword", newPassword.getText().toString());
        request.put("confirmNewPassword", confirmNewPassword.getText().toString());

        new UpdateUserInformationsDbOperation(getApplicationContext()).execute(request);
    }

    private void onClickUpdateUser() {
        Button updateButton = (Button) findViewById(R.id.updtateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInformations();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        nicknameUser = session.getUserDetails().get("nickname");

        askInformationsAboutUser();

        onClickUpdateUser();
    }
}
