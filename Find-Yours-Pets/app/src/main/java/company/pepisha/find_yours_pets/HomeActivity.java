package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.animal.AnimalOperation;

public class HomeActivity extends Activity {

    GridLayout petsGrid;
    AnimalOperation animalOperation;

    private void addAnimals(List<Animal> animals) {

        for (Animal a : animals) {
            LinearLayout petLayout = new LinearLayout(this);
            petLayout.setOrientation(LinearLayout.VERTICAL);

            ImageButton petPicture = new ImageButton(this);
            petPicture.setImageResource(R.drawable.dog);

            TextView petName = new TextView(this);
            petName.setText(a.getName());
            petName.setGravity(Gravity.CENTER);

            petLayout.addView(petPicture);
            petLayout.addView(petName);

            petsGrid.addView(petLayout);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        petsGrid = (GridLayout) findViewById(R.id.petsGrid);

        animalOperation = new AnimalOperation(this);
        animalOperation.open();

        addAnimals(animalOperation.getAllAnimals());

        animalOperation.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
