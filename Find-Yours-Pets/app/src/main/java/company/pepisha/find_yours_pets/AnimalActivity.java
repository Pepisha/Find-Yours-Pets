package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.animal.AnimalOperation;

public class AnimalActivity extends Activity {

    private Animal getAnimal(int id) {
        AnimalOperation animalOperation = new AnimalOperation(this);
        animalOperation.open();

        Animal animal = animalOperation.getAnimal(id);

        animalOperation.close();

        return animal;
    }

    private void fillAnimalFields(Animal animal) {
        TextView animalName = (TextView) findViewById(R.id.animalName);
        animalName.setText(animal.getName());

        TextView animalBreed = (TextView) findViewById(R.id.animalBreed);
        animalBreed.setText(animal.getBreed());

        TextView animalAge = (TextView) findViewById(R.id.animalAge);
        animalAge.setText(animal.getAge());

        TextView animalGender = (TextView) findViewById(R.id.animalGender);
        animalGender.setText((animal.getGender() == Animal.Gender.MALE) ? "Male" : "Femelle");

        TextView animalDescription = (TextView) findViewById(R.id.animalDescription);
        animalDescription.setText(animal.getDescription());

        TextView animalState = (TextView) findViewById(R.id.animalState);
        animalState.setText((animal.getState() == Animal.ADOPTION) ? "A l'adoption" : "Adopté");

        Button stateButton = (Button) findViewById(R.id.stateButton);
        stateButton.setText((animal.getState() == Animal.ADOPTION) ? "Adopté" : "De retour");

        ImageView animalPicture = (ImageView) findViewById(R.id.animalPicture);
        animalPicture.setImageDrawable(getResources().getDrawable(R.drawable.dog));
    }

    private void onClickChangeAnimalPhoto(){
        Button changeAnimalPhoto = (Button) findViewById(R.id.pictureButton);
        changeAnimalPhoto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent cameraScreen = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(cameraScreen);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        int animalId = getIntent().getIntExtra("animalId", 1);
        Animal animal = getAnimal(animalId);

        fillAnimalFields(animal);
        onClickChangeAnimalPhoto();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animal, menu);
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
