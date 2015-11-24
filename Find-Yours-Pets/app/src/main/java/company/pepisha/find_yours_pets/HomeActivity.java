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

import java.util.HashMap;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;

public class HomeActivity extends Activity {

    GridLayout petsGrid;

    private void addAnimals(HashMap<String, Object> animals) {

        for (Map.Entry<String, Object> entry : animals.entrySet()) {
            Animal a = (Animal) entry.getValue();

            LinearLayout petLayout = new LinearLayout(petsGrid.getContext());
            petLayout.setOrientation(LinearLayout.VERTICAL);

            ImageButton petPicture = new ImageButton(petsGrid.getContext());
            petPicture.setImageResource(R.drawable.dog);
            petPicture.setId(Integer.parseInt(entry.getKey()));

            TextView petName = new TextView(petsGrid.getContext());
            petName.setText(a.getName());
            petName.setGravity(Gravity.CENTER);

            petLayout.addView(petPicture);
            petLayout.addView(petName);

            petsGrid.addView(petLayout);

            petPicture.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent animalScreen = new Intent(getApplicationContext(), AnimalActivity.class);
                    animalScreen.putExtra("animalId", v.getId());

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
