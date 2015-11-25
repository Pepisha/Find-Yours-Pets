package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;

public class SheltersActivity extends BaseActivity {

    ListView sheltersList;

    private class GetSheltersDbOperation extends ServerDbOperation {

        public GetSheltersDbOperation(Context c) {
            super(c, "getAllShelters");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            addShelters(result);
        }
    }

    private void addShelters(HashMap<String, Object> shelters) {

        List<Shelter> shelterObjects = new ArrayList<Shelter>();

        for (Map.Entry<String, Object> entry : shelters.entrySet()) {
            ParcelableShelter s = new ParcelableShelter((JSONObject) entry.getValue());
            shelterObjects.add(s);
        }

        ArrayAdapter<Shelter> listAdapter = new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, shelterObjects);
        sheltersList.setAdapter(listAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelters);

        sheltersList = (ListView) findViewById(R.id.sheltersList);

        new GetSheltersDbOperation(getApplicationContext()).execute(new HashMap<String, String>());

        sheltersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent shelterScreen = new Intent(getApplicationContext(), ShelterActivity.class);
                ParcelableShelter s = (ParcelableShelter) sheltersList.getItemAtPosition(position);
                shelterScreen.putExtra("shelter", s);
                startActivity(shelterScreen);
            }
        });

        Button addShelterButton = (Button) findViewById(R.id.button);

        addShelterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent addShelterScreen = new Intent(getApplicationContext(), AddShelterActivity.class);
                startActivity(addShelterScreen);
            }
        });
    }
}
