package company.pepisha.find_yours_pets;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.opinion.Opinion;
import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;
import company.pepisha.find_yours_pets.views.OpinionAdapter;

public class ShelterCommentsActivity extends BaseActivity {

    private ListView opinionsList;

    private Shelter shelter;

    private class GetOpinionsAboutShelterDbOperation extends ServerDbOperation {

        public GetOpinionsAboutShelterDbOperation(Context c) {
            super(c, "getOpinionsAboutShelter");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            addOpinions(result);
        }
    }

    private void addOpinions(HashMap<String, Object> opinions) {
        List<Opinion> opinionObjects = new ArrayList<Opinion>();

        for (Map.Entry<String, Object> entry : opinions.entrySet()) {
            Opinion o = new Opinion((JSONObject) entry.getValue());
            opinionObjects.add(o);
        }

        OpinionAdapter listAdapter = new OpinionAdapter(this, R.layout.opinion_layout, opinionObjects);
        opinionsList.setAdapter(listAdapter);
    }

    private void loadComments() {
        HashMap<String, String> opinionsRequest = new HashMap<String, String>();
        opinionsRequest.put("idShelter", Integer.toString(shelter.getIdShelter()));
        new GetOpinionsAboutShelterDbOperation(this).execute(opinionsRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_comments);

        shelter = (ParcelableShelter) getIntent().getParcelableExtra("shelter");
        opinionsList = (ListView) findViewById(R.id.opinionsList);

        loadComments();
    }


}
