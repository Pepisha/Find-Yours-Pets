package company.pepisha.find_yours_pets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;

public class HomeActivity extends BaseActivity {

    private GridLayout petsGrid;

    private Map<Integer, Animal> animalsList = new HashMap<Integer, Animal>();

    private void addAnimals(HashMap<String, Object> animals) {

        for (Map.Entry<String, Object> entry : animals.entrySet()) {
            int animalId = Integer.parseInt(entry.getKey());
            ParcelableAnimal a = new ParcelableAnimal((JSONObject) entry.getValue());
            animalsList.put(animalId, a);

            LinearLayout petLayout = new LinearLayout(petsGrid.getContext());
            petLayout.setOrientation(LinearLayout.VERTICAL);

            ImageButton petPicture = new ImageButton(petsGrid.getContext());
            petPicture.setImageResource(R.drawable.dog);
            petPicture.setId(animalId);

            TextView petName = new TextView(petsGrid.getContext());
            petName.setText(a.getName());
            petName.setGravity(Gravity.CENTER);

            petLayout.addView(petPicture);
            petLayout.addView(petName);

            petsGrid.addView(petLayout);

            petPicture.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent animalScreen = new Intent(getApplicationContext(), AnimalActivity.class);
                    animalScreen.putExtra("animal", (ParcelableAnimal) animalsList.get(v.getId()));

                    startActivity(animalScreen);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        petsGrid = (GridLayout) findViewById(R.id.petsGrid);
        new GetAnimalsDbOperation(getApplicationContext()).execute(new HashMap<String, String>());

        Button addAnimalButton = (Button) findViewById(R.id.button);

        addAnimalButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent addAnimalScreen = new Intent(getApplicationContext(), AddAnimalActivity.class);
                startActivity(addAnimalScreen);
            }
        });
    }

    private class GetAnimalsDbOperation extends ServerDbOperation {

        public GetAnimalsDbOperation(Context c) {
            super(c, "getHomelessAnimals");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {

            addAnimals(result);
        }
    }
}
