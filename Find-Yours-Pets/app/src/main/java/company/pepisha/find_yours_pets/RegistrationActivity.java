package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;

public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registrationButton = (Button) findViewById(R.id.registrationButton);

        registrationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText nickname = (EditText) findViewById(R.id.nickname);
                EditText password = (EditText) findViewById(R.id.password);
                EditText passwordConfirmation = (EditText) findViewById(R.id.passwordConfirmation);
                EditText mail = (EditText) findViewById(R.id.mail);
                EditText phone = (EditText) findViewById(R.id.phone);
                EditText firstname = (EditText) findViewById(R.id.firstname);
                EditText lastname = (EditText) findViewById(R.id.lastname);

                HashMap<String, String> request = new HashMap<String, String>();
                request.put("page", "register");
                request.put("nickname", nickname.getText().toString());
                request.put("password1", password.getText().toString());
                request.put("password2", passwordConfirmation.getText().toString());
                request.put("mail", mail.getText().toString());
                request.put("phone", phone.getText().toString());
                request.put("firstname", firstname.getText().toString());
                request.put("lastname", lastname.getText().toString());

                new RegistrationDbOperation(getApplicationContext()).execute(request);
            }
        });
    }

    private class RegistrationDbOperation extends ServerDbOperation {
        public RegistrationDbOperation(Context c) {
            super(c);
        }

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            if (result.get("success").equals("true")) {
                Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_LONG).show();

                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeScreen);
            } else {
                Toast.makeText(getApplicationContext(), result.get("error"), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
