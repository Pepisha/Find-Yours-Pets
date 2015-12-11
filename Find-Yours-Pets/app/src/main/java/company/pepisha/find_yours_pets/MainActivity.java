package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.session.SessionManager;

public class MainActivity extends BaseActivity {

    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectionButton = (Button) findViewById(R.id.connectionButton);
        Button registrationButton = (Button) findViewById(R.id.registrationButton);

        //Listening to button event
        connectionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText nicknameEdit = (EditText) findViewById(R.id.nickname);
                EditText passwordEdit = (EditText) findViewById(R.id.password);

                HashMap<String, String> request = new HashMap<String, String>();
                request.put("nickname", nicknameEdit.getText().toString());
                nickname = nicknameEdit.getText().toString();
                request.put("password", passwordEdit.getText().toString());

                new ConnectionDbOperation(getApplicationContext()).execute(request);
            }
        });

        //Listening to button event
        registrationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent registrationScreen = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(registrationScreen);
            }
        });
    }

    private class ConnectionDbOperation extends ServerDbOperation {
        public ConnectionDbOperation(Context c) {
            super(c, "login");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            String toastText = "";

            if (successResponse(result)) {
                toastText = getString(R.string.successConnection);
                Boolean isAdmin =  Boolean.valueOf(result.get("isAdmin").toString());
                session.createLoginSession(nickname,isAdmin);
                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeScreen);
            } else {
                toastText = getString(R.string.failureConnection);
            }

            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_disconnected, menu);
        return true;
    }
}
