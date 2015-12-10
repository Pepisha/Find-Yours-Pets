package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.HashMap;
import java.util.List;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.AnimalConstants;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalType;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalTypeOperation;
import company.pepisha.find_yours_pets.facebook.FacebookManager;

public class AddAnimalActivity extends BaseActivity {

    private AnimalTypeOperation animalTypeDbOperation;
    ShareDialog shareDialog;

    private class AddAnimalDbOperation extends ServerDbOperation {
        public AddAnimalDbOperation(Context c) {
            super(c, "addAnimalInShelter");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                Toast.makeText(getApplicationContext(), R.string.animalAdded, Toast.LENGTH_SHORT).show();

                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeScreen);
            }
        }
    }

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
        request.put("gender", (checkedAnimalGender.getText().toString().equals(getResources().getString(R.string.male)))
                                            ? AnimalConstants.MALE : AnimalConstants.FEMALE);
        request.put("breed", breedAnimal.getText().toString());
        request.put("age", ageAnimal.getText().toString());
        request.put("catsFriend", catsFriend.getText().toString());
        request.put("dogsFriend", dogsAgreements.getText().toString());
        request.put("childrenFriend", childrenAgreements.getText().toString());
        request.put("description", description.getText().toString());
        request.put("idShelter", Integer.toString(1));

        new AddAnimalDbOperation(getApplicationContext()).execute(request);
    }

    private void shareAnimalOnFacebook() {
        EditText animalName = (EditText) findViewById(R.id.animalName);
        RadioGroup radioGroupAnimalTypes = (RadioGroup) findViewById(R.id.radioGroupAnimalTypes);
        RadioGroup radioGroupAnimalGender = (RadioGroup) findViewById(R.id.radioGroupAnimalSexe);
        EditText breedAnimal = (EditText) findViewById(R.id.breedAnimal);
        EditText ageAnimal = (EditText) findViewById(R.id.ageAnimal);
        EditText catsFriend = (EditText) findViewById(R.id.catsFriend);
        EditText dogsAgreements = (EditText) findViewById(R.id.dogsAgreements);
        EditText childrenAgreements = (EditText) findViewById(R.id.childrenAgreements);
        EditText description = (EditText) findViewById(R.id.description);

        String postTitle = animalName.getText().toString();
        String postContent = animalName.getText().toString()+"\n"
                + breedAnimal.getText().toString()+"\n"
                + ageAnimal.getText().toString()+"\n"
                + catsFriend.getText().toString()+"\n"
                + dogsAgreements.getText().toString()+"\n"
                + childrenAgreements.getText().toString()+"\n"
                + description.getText().toString();

        shareDialog = new ShareDialog(this);
        shareDialog.show(FacebookManager.share(postTitle, postContent, ServerConnectionManager.url));

        //TODO ajouter image quand l'upload d'images marchera
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
                shareAnimalOnFacebook();
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


}
