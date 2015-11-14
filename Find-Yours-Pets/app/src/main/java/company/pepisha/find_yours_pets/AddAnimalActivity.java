package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import company.pepisha.find_yours_pets.db.animal.animalType.AnimalType;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalTypeOperation;

public class AddAnimalActivity extends Activity {

    private AnimalTypeOperation animalTypeDbOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        createTypesRadioButtons();
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
