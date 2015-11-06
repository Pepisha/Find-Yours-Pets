package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import company.pepisha.find_yours_pets.db.user.UserOperation;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectionButton = (Button) findViewById(R.id.connectionButton);
        Button registrationButton = (Button) findViewById(R.id.registrationButton);

        //Listening to button event
        connectionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                UserOperation userDBOperation = new UserOperation(v.getContext());
                try {
                    userDBOperation.open();

                    EditText nickname = (EditText) findViewById(R.id.nickname);
                    EditText password = (EditText) findViewById(R.id.password);

                    String toastText = "";
                    if (userDBOperation.userConnection(nickname.getText().toString(), password.getText().toString())) {
                        toastText = getString(R.string.successConnection);

                        /*Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(homeScreen);*/
                    } else {
                        toastText = getString(R.string.successConnection);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
                    toast.show();

                } catch(android.database.SQLException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "DB error :" + e.toString(), Toast.LENGTH_SHORT);
                    toast.show();
                }

                userDBOperation.close();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
