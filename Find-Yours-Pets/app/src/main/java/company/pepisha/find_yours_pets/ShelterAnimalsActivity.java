package company.pepisha.find_yours_pets;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridLayout;

import java.util.HashMap;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;
import company.pepisha.find_yours_pets.views.AnimalViews;

public class ShelterAnimalsActivity extends BaseActivity {

    private GridLayout petsGrid;

    private Shelter shelter;

    private Map<Integer, Animal> animalsList = new HashMap<>();

    private class GetSheltersAnimalsDbOperation extends ServerDbOperation {

        public GetSheltersAnimalsDbOperation(Context c) {
            super(c, "getSheltersAnimals");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null) {
                addAnimals(result);
            }
        }
    }

    private void addAnimals(HashMap<String, Object> animals) {
        animalsList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(petsGrid, animalsList, session);
    }

    private void loadAnimals() {
        HashMap<String, String> animalsRequest = new HashMap<String, String>();
        animalsRequest.put("idShelter", Integer.toString(shelter.getIdShelter()));
        animalsRequest.put("nickname", session.getUserDetails().get("nickname"));
        new GetSheltersAnimalsDbOperation(this).execute(animalsRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_animals);

        shelter = (ParcelableShelter) getIntent().getParcelableExtra("shelter");
        petsGrid = (GridLayout) findViewById(R.id.petsGrid);

        loadAnimals();
    }


}
