package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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
import company.pepisha.find_yours_pets.socialNetworksManagers.FacebookManager;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;
import company.pepisha.find_yours_pets.socialNetworksManagers.TwitterManager;
import company.pepisha.find_yours_pets.views.AnimalViews;
import company.pepisha.find_yours_pets.views.OpinionAdapter;

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
        shelterName.setText(shelter.getName());

        RatingBar shelterNote = (RatingBar) findViewById(R.id.shelterNote);
        if (shelter.getStars() != 0) {
            shelterNote.setRating((float) shelter.getStars());
        } else {
            shelterNote.setVisibility(View.INVISIBLE);
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

        OpinionAdapter listAdapter = new OpinionAdapter(this, R.layout.opinion_layout, opinionObjects);
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
        final ImageButton shareOnFacebook = (ImageButton) findViewById(R.id.shareFacebookButton);
        shareOnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOnFacebook();
            }
        });
    }

    private void tweet() {
        String website = shelter.getWebsite() == null ? "" : " - "+shelter.getWebsite();
        String description = shelter.getDescription() + website;
        TwitterManager.tweetWithoutImage(description, this);
    }

    private void onClickTweet() {
        final ImageButton tweet = (ImageButton) findViewById(R.id.tweetButton);
        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet();
            }
        });
    }

    private void commentShelter() {
        String userNickname = session.getUserDetails().get("nickname");
        EditText commentBox = (EditText) findViewById(R.id.shelterComment);
        String comment = commentBox.getText().toString();

        RatingBar ratingBar = (RatingBar) findViewById(R.id.opinionNote);
        String note = Float.toString(ratingBar.getRating());

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
        onClickCommentButton();
    }

    private void onClickCallShelter() {
        final ImageButton callButton = (ImageButton) findViewById(R.id.callButton);
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

    private void onClickSeeAllAnimals() {
        final Button seeAllAnimalsButton = (Button) findViewById(R.id.seeAllAnimalsButton);
        seeAllAnimalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent animalsScreen = new Intent(getApplicationContext(), ShelterAnimalsActivity.class);
                ParcelableShelter s = (ParcelableShelter) shelter;
                animalsScreen.putExtra("shelter", s);
                startActivity(animalsScreen);
            }
        });
    }

    private void onClickSeeAllComments() {
        final Button seeAllCommentsButton = (Button) findViewById(R.id.seeAllCommentsButton);
        seeAllCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentsScreen = new Intent(getApplicationContext(), ShelterCommentsActivity.class);
                ParcelableShelter s = (ParcelableShelter) shelter;
                commentsScreen.putExtra("shelter", s);
                startActivity(commentsScreen);
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
        animalsRequest.put("numberOfAnimals", Integer.toString(3));
        new GetSheltersAnimalsDbOperation(this).execute(animalsRequest);

        HashMap<String, String> opinionsRequest = new HashMap<String, String>();
        opinionsRequest.put("idShelter", Integer.toString(shelter.getIdShelter()));
        opinionsRequest.put("numberOfOpinions", Integer.toString(3));
        new GetOpinionsAboutShelterDbOperation(this).execute(opinionsRequest);

        onClickShareOnFacebook();
        onClickTweet();
        onClickCallShelter();
        onClickSeeAllAnimals();
        onClickSeeAllComments();
        addAddAnimalButtonIfShelterAdministrator();
    }


}
