package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.fileExplorer.FileExplorer;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;

public class AnimalActivity extends BaseActivity {

    private Animal animal;

    private class ChangeAnimalStatusDbOperation extends ServerDbOperation {
        public ChangeAnimalStatusDbOperation(Context c) {
            super(c, "changeAnimalsStatus");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            String toastText = "";

            if(successResponse(result)){
                toastText = getString(R.string.successUpdateAnimalStatus);
                refreshAnimalState();
            } else {
                toastText = "Failed status change";
            }

            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshAnimalState() {
        animal.setState((animal.getState() == 1) ? 2 : 1);

        TextView animalState = (TextView) findViewById(R.id.animalState);
        animalState.setText((animal.getState() == Animal.ADOPTION)
                ? getResources().getString(R.string.adoption) : getResources().getString(R.string.adopted));

        Button stateButton = (Button) findViewById(R.id.stateButton);
        stateButton.setText((animal.getState() == Animal.ADOPTION)
                ? getResources().getString(R.string.adopted) : getResources().getString(R.string.adoption));
    }

    private void setAnimalState(int state) {
        HashMap<String, String> request = new HashMap<>();
        request.put("idAnimal", Integer.toString(animal.getIdAnimal()));
        request.put("newStatus", Integer.toString(state));

        new ChangeAnimalStatusDbOperation(getApplicationContext()).execute(request);
    }

    private void fillAnimalFields() {
        TextView animalName = (TextView) findViewById(R.id.animalName);
        animalName.setText(animal.getName());

        TextView animalBreed = (TextView) findViewById(R.id.animalBreed);
        animalBreed.setText(animal.getBreed());

        TextView animalAge = (TextView) findViewById(R.id.animalAge);
        animalAge.setText(animal.getAge());

        TextView animalGender = (TextView) findViewById(R.id.animalGender);
        animalGender.setText((animal.getGender() == Animal.Gender.MALE)
                ? getResources().getString(R.string.male) : getResources().getString(R.string.female));

        TextView animalDescription = (TextView) findViewById(R.id.animalDescription);
        animalDescription.setText(animal.getDescription());

        ImageView animalPicture = (ImageView) findViewById(R.id.animalPicture);
        animalPicture.setImageDrawable(getResources().getDrawable(R.drawable.dog));

        refreshAnimalState();

        Button stateButton = (Button) findViewById(R.id.stateButton);
        stateButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int newStatus = animal.getState() == 1 ? 2 : 1;
                setAnimalState(newStatus);
            }
        });
    }

    private void onClickChangeAnimalPhoto() {
        Button changeAnimalPhoto = (Button) findViewById(R.id.pictureButton);
        changeAnimalPhoto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent cameraScreen = new Intent(getApplicationContext(), CameraActivity.class);
                //Intent cameraScreen = new Intent(getApplicationContext(), FileExplorer.class);
                startActivity(cameraScreen);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        animal = (ParcelableAnimal) getIntent().getParcelableExtra("animal");

        fillAnimalFields();
        onClickChangeAnimalPhoto();
    }
}
