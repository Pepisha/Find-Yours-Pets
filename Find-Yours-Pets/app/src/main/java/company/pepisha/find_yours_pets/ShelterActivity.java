package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.opinion.Opinion;
import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.facebook.FacebookManager;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;
import company.pepisha.find_yours_pets.views.AnimalViews;

public class ShelterActivity extends BaseActivity {

    private Shelter shelter;

    private ShareDialog shareDialog;

    private GridLayout petsGrid;

    private Map<Integer, Animal> animalsList = new HashMap<>();

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

    private class AddCommentToShelterDbOperation extends ServerDbOperation {

        public AddCommentToShelterDbOperation(Context c) { super(c, "giveOpinionAboutShelter"); }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if(successResponse(result)) {
                Toast.makeText(getApplicationContext(), "Comment added", Toast.LENGTH_SHORT).show();
                Intent shelterScreen = new Intent(getApplicationContext(), ShelterActivity.class);
                ParcelableShelter s = (ParcelableShelter) shelter;
                shelterScreen.putExtra("shelter", s);
                startActivity(shelterScreen);
            }
        }
    }

    private class IsShelterAdministratorDbOperation extends ServerDbOperation {
        public IsShelterAdministratorDbOperation(Context c) {
            super(c, "isShelterAdministrator");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result.get("admin").equals(true)) {
                addCreateAnimalButton();
            }
        }
    }

    private void fillShelterFields() {
        TextView shelterName = (TextView) findViewById(R.id.shelterName);
        if(shelter.getStars() != 0) {
            shelterName.setText(shelter.getName()+" - "+shelter.getStars());
        } else {
            shelterName.setText(shelter.getName());
            ImageView shelterStar = (ImageView) findViewById(R.id.shelterStar);
            shelterStar.setVisibility(View.INVISIBLE);
        }


        TextView shelterDescription = (TextView) findViewById(R.id.shelterDescription);
        shelterDescription.setText(shelter.getDescription());

        TextView shelterAddress = (TextView) findViewById(R.id.shelterAddress);
        shelterAddress.setText(Integer.toString(shelter.getIdAddress()));

        TextView shelterMail = (TextView) findViewById(R.id.shelterMail);
        shelterMail.setText(shelter.getMail());

        TextView shelterPhone = (TextView) findViewById(R.id.shelterPhone);
        shelterPhone.setText(shelter.getPhone());

        if(shelter.getWebsite() != null) {
            TextView shelterWebsite = (TextView) findViewById(R.id.shelterWebSite);
            shelterWebsite.setText(shelter.getWebsite());
        }

    }

    private void addAnimals(HashMap<String, Object> animals) {
        animalsList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(petsGrid, animalsList, session);
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

    private void shareOnFacebook() {
        String title = shelter.getName();
        String description = shelter.getDescription();
        String url = shelter.getWebsite() == null ? ServerConnectionManager.url : shelter.getWebsite();
        shareDialog = new ShareDialog(this);
        shareDialog.show(FacebookManager.share(title, description, url));
    }

    private void onClickShareOnFacebook() {
        final Button shareOnFacebook = (Button) findViewById(R.id.shareFacebookButton);
        shareOnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnFacebook();
            }
        });
    }

    private void initialiseNoteNumberPicker() {
        NumberPicker shelterNote = (NumberPicker) findViewById(R.id.shelterNote);
        shelterNote.setMaxValue(5);
        shelterNote.setMinValue(0);
    }

    private void commentShelter() {
        String userNickname = session.getUserDetails().get("nickname");
        EditText commentBox = (EditText) findViewById(R.id.shelterComment);
        String comment = commentBox.getText().toString();

        NumberPicker notePicker = (NumberPicker) findViewById(R.id.shelterNote);
        String note = Integer.toString(notePicker.getValue());

        HashMap<String, String> request = new HashMap<String, String>();
        request.put("idShelter", Integer.toString(shelter.getIdShelter()));
        request.put("nickname", userNickname);
        request.put("stars", note);
        request.put("description", comment);

        new AddCommentToShelterDbOperation(getApplicationContext()).execute(request);
    }

    private void onClickCommentButton() {
        final Button commentButton = (Button) findViewById(R.id.commentButton);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentShelter();
            }
        });
    }

    private void initialiseShelterComment() {
        initialiseNoteNumberPicker();
        onClickCommentButton();
    }

    private void onClickCallShelter() {
        final Button callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + shelter.getPhone()));
                startActivity(callIntent);
            }
        });
    }

    private void addAddAnimalButtonIfShelterAdministrator() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("idShelter", Integer.toString(shelter.getIdShelter()));
        request.put("nickname", session.getUserDetails().get("nickname"));

        new IsShelterAdministratorDbOperation(this).execute(request);
    }

    private void addCreateAnimalButton() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        final Button addAnimalButton = new Button(this);
        addAnimalButton.setText(getResources().getString(R.string.addAnimal));
        layout.addView(addAnimalButton);

        addAnimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAnimalScreen = new Intent(getApplicationContext(), AddAnimalActivity.class);

                addAnimalScreen.putExtra("idShelter", shelter.getIdShelter());
                startActivity(addAnimalScreen);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);

        shelter = (ParcelableShelter) getIntent().getParcelableExtra("shelter");
        petsGrid = (GridLayout) findViewById(R.id.petsGrid);
        opinionsList = (ListView) findViewById(R.id.opinionsList);

        initialiseShelterComment();

        fillShelterFields();

        HashMap<String, String> animalsRequest = new HashMap<String, String>();
        animalsRequest.put("idShelter", Integer.toString(shelter.getIdShelter()));
        animalsRequest.put("nickname", session.getUserDetails().get("nickname"));
        new GetSheltersAnimalsDbOperation(this).execute(animalsRequest);

        HashMap<String, String> opinionsRequest = new HashMap<String, String>();
        opinionsRequest.put("idShelter", Integer.toString(shelter.getIdShelter()));
        new GetOpinionsAboutShelterDbOperation(this).execute(opinionsRequest);

        onClickShareOnFacebook();
        onClickCallShelter();
        addAddAnimalButtonIfShelterAdministrator();
    }


}
