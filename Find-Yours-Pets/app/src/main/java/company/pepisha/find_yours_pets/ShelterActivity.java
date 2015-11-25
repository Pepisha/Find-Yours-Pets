package company.pepisha.find_yours_pets;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.opinion.Opinion;
import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;

public class ShelterActivity extends BaseActivity {

    private Shelter shelter;

    private ListView opinionsList;

    private class GetOpinionsAboutShelterDbOperation extends ServerDbOperation {

        public GetOpinionsAboutShelterDbOperation(Context c) {
            super(c, "getOpinionsAboutShelter");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            addOpinions(result);
        }
    }

    private void fillShelterFields() {
        TextView shelterName = (TextView) findViewById(R.id.shelterName);
        shelterName.setText(shelter.getName());

        TextView shelterDescription = (TextView) findViewById(R.id.shelterDescription);
        shelterDescription.setText(shelter.getDescription());

        TextView shelterAddress = (TextView) findViewById(R.id.shelterAddress);
        shelterAddress.setText(Integer.toString(shelter.getIdAddress()));

        TextView shelterMail = (TextView) findViewById(R.id.shelterMail);
        shelterMail.setText(shelter.getMail());

        TextView shelterPhone = (TextView) findViewById(R.id.shelterPhone);
        shelterPhone.setText(shelter.getPhone());
    }

    private void addOpinions(HashMap<String, Object> opinions) {
        List<Opinion> opinionObjects = new ArrayList<Opinion>();

        for (Map.Entry<String, Object> entry : opinions.entrySet()) {
            Opinion o = new Opinion((JSONObject) entry.getValue());
            opinionObjects.add(o);
        }

        ArrayAdapter<Opinion> listAdapter = new ArrayAdapter<Opinion>(this, android.R.layout.simple_list_item_1, opinionObjects);
        opinionsList.setAdapter(listAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);

        shelter = (ParcelableShelter) getIntent().getParcelableExtra("shelter");
        opinionsList = (ListView) findViewById(R.id.opinionsList);

        fillShelterFields();

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("idShelter", Integer.toString(shelter.getIdShelter()));
        new GetOpinionsAboutShelterDbOperation(getApplicationContext()).execute(request);
    }
}
