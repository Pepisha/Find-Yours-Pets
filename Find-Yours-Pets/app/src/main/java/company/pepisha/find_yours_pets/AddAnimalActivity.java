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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.AnimalStateConstants;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalType;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalTypeOperation;

public class AddAnimalActivity extends Activity {

    private AnimalTypeOperation animalTypeDbOperation;

    private void animalAdding() {
        EditText animalName = (EditText) findViewById(R.id.animalName);
        RadioGroup radioGroupAnimalTypes = (RadioGroup) findViewById(R.id.radioGroupAnimalTypes);
        RadioGroup radioGroupAnimalGender = (RadioGroup) findViewById(R.id.radioGroupAnimalSexe);
        EditText breedAnimal = (EditText) findViewById(R.id.breedAnimal);
        EditText ageAnimal = (EditText) findViewById(R.id.ageAnimal);
        EditText catsFriend = (EditText) findViewById(R.id.catsFriend);
        EditText dogsAgreements = (EditText) findViewById(R.id.dogsAgreements);
        EditText childrenAgreements = (EditText) findViewById(R.id.childrenAgreements);
        EditText description = (EditText) findViewById(R.id.description);

        RadioButton checkedAnimalGender = (RadioButton) findViewById(radioGroupAnimalGender.getCheckedRadioButtonId());

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("name", animalName.getText().toString());
        request.put("type", Integer.toString(radioGroupAnimalTypes.getCheckedRadioButtonId()));
        request.put("gender", checkedAnimalGender.getText().toString());
        request.put("breed", breedAnimal.getText().toString());
        request.put("age", ageAnimal.getText().toString());
        request.put("catsFriend", catsFriend.getText().toString());
        request.put("dogsFriend", dogsAgreements.getText().toString());
        request.put("childrenFriend", childrenAgreements.getText().toString());
        request.put("description", description.getText().toString());
        request.put("state", Integer.toString(AnimalStateConstants.ADOPTION_ID));
        request.put("idShelter", Integer.toString(1));

        new AddAnimalDbOperation(getApplicationContext()).execute(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        createTypesRadioButtons();

        Button createButton = (Button) findViewById(R.id.addAnimalButton);

        createButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                animalAdding();
            }
        });
    }

    public void createTypesRadioButtons(){

        animalTypeDbOperation = new AnimalTypeOperation(this);
        animalTypeDbOperation.open();
        List<AnimalType> animalTypes = animalTypeDbOperation.getAllAnimalTypes();
        animalTypeDbOperation.close();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupAnimalTypes);

        for(AnimalType type : animalTypes){
            RadioButton rb = new RadioButton(this);
            rb.setText(type.getName());
            rb.setId(type.getIdAnimalType());
            radioGroup.addView(rb);
        }
    }

    private class AddAnimalDbOperation extends ServerDbOperation {
        public AddAnimalDbOperation(Context c) {
            super(c, "addAnimal");
        }

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            if (successResponse(result)) {
                Toast.makeText(getApplicationContext(), R.string.animalAdded, Toast.LENGTH_SHORT).show();

                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeScreen);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_animal, menu);
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
