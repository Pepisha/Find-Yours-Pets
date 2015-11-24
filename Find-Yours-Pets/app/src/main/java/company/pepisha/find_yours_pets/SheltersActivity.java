package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
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

        List<String> shelterStrings = new ArrayList<String>();

        for (Map.Entry<String, Object> entry : shelters.entrySet()) {
            int shelterId = Integer.parseInt(entry.getKey());
            ParcelableShelter s = new ParcelableShelter((JSONObject) entry.getValue());
            shelterStrings.add(s.getName() + " (" + s.getMail() + ")");

            /*shelterElement.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent shelterScreen = new Intent(getApplicationContext(), ShelterActivity.class);
                    shelterScreen.putExtra("shelter", (ParcelableShelter) animalsList.get(v.getId()));

                    startActivity(shelterScreen);
                }
            });*/
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shelterStrings);
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
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, SendMessage.class);
                String message = "abc";
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
    }
}
