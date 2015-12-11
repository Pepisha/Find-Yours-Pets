package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.HashMap;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.views.AnimalViews;

public class HomeActivity extends BaseActivity {

    private GridLayout petsGrid;

    private class GetAnimalsDbOperation extends ServerDbOperation {

        public GetAnimalsDbOperation(Context c) {
            super(c, "getHomelessAnimals");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            addAnimals(result);
        }
    }

    private void addAnimals(HashMap<String, Object> animals) {
        AnimalViews.addAnimalsToGrid(this, animals, petsGrid);
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
}
