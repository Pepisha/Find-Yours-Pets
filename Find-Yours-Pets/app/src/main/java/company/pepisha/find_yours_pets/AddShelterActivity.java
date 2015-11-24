package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;

public class AddShelterActivity extends BaseActivity {

    private class AddShelterDbOperation extends ServerDbOperation {

        public AddShelterDbOperation(Context c) {
            super(c, "addShelter");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                Toast.makeText(getApplicationContext(), R.string.shelterAdded, Toast.LENGTH_SHORT).show();

                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeScreen);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shelter);

        Button createButton = (Button) findViewById(R.id.addShelterButton);

        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                shelterAdding();
            }
        });
    }

    private void shelterAdding() {
        EditText name = (EditText) findViewById(R.id.shelterName);
        EditText phone = (EditText) findViewById(R.id.shelterPhone);
        EditText description = (EditText) findViewById(R.id.shelterDescription);
        EditText mail = (EditText) findViewById(R.id.shelterMail);
        EditText operationalHours = (EditText) findViewById(R.id.shelteoperationalHours);
        EditText street = (EditText) findViewById(R.id.street);
        EditText zipcode = (EditText) findViewById(R.id.zipcode);
        EditText city = (EditText) findViewById(R.id.city);

        HashMap<String, String> request = new HashMap<>();
        request.put("name", name.getText().toString());
        request.put("phone", phone.getText().toString());
        request.put("description", description.getText().toString());
        request.put("mail", mail.getText().toString());
        request.put("operationalHours", operationalHours.getText().toString());
        request.put("street", street.getText().toString());
        request.put("zipcode", zipcode.getText().toString());
        request.put("city", city.getText().toString());

        new AddShelterDbOperation(getApplicationContext()).execute(request);

    }
}

