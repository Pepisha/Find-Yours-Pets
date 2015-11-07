package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import company.pepisha.find_yours_pets.db.user.UserOperation;
import company.pepisha.find_yours_pets.tools.RegistrationCheck;

public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registrationButton = (Button) findViewById(R.id.registrationButton);

        registrationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                UserOperation userDBOperation = new UserOperation(v.getContext());
                try {
                    userDBOperation.open();

                    EditText nickname = (EditText) findViewById(R.id.nickname);
                    EditText password = (EditText) findViewById(R.id.password);
                    EditText passwordConfirmation = (EditText) findViewById(R.id.passwordConfirmation);
                    EditText mail = (EditText) findViewById(R.id.mail);
                    EditText phone = (EditText) findViewById(R.id.phone);
                    EditText firstname = (EditText) findViewById(R.id.firstname);
                    EditText lastname = (EditText) findViewById(R.id.lastname);

                    if (RegistrationCheck.passwordsMatch(password.getText().toString(), passwordConfirmation.getText().toString())) {

                        userDBOperation.addUser(nickname.getText().toString(),
                                password.getText().toString(),
                                mail.getText().toString(),
                                phone.getText().toString(),
                                firstname.getText().toString(),
                                lastname.getText().toString());
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Inscription de " + nickname.getText().toString(), Toast.LENGTH_LONG);
                    toast.show();
                } catch(android.database.SQLException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "DB error :" + e.toString(), Toast.LENGTH_LONG);
                    toast.show();
                }

                userDBOperation.close();
            }
        });
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
