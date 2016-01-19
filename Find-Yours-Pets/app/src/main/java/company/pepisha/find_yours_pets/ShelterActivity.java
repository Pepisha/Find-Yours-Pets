package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
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

    private class FollowShelterDbOperation extends ServerDbOperation {
        public FollowShelterDbOperation(Context c) {
            super(c, "followShelter");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                setShelterFollowing(true);
                Toast.makeText(getApplicationContext(), R.string.followShelter, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UnfollowShelterDbOperation extends ServerDbOperation {
        public UnfollowShelterDbOperation(Context c) {
            super(c, "unfollowShelter");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                setShelterFollowing(false);
                Toast.makeText(getApplicationContext(), R.string.unfollowShelter, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AddCommentToShelterDbOperation extends ServerDbOperation {

        public AddCommentToShelterDbOperation(Context c) { super(c, "giveOpinionAboutShelter"); }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                Toast.makeText(getApplicationContext(), "Comment added", Toast.LENGTH_SHORT).show();
                shelter = new ParcelableShelter((JSONObject) result.get("shelter"));
                fillShelterFields();
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
                addSeeShelterMessagesButton();
            } else {
                addSendShelterMessageButton();
            }
        }
    }

    private void addWebsiteRow(final String website) {
        TableRow websiteRow = (TableRow) findViewById(R.id.websiteRow);

        TextView websiteText = new TextView(this);
        websiteText.setText(getResources().getString(R.string.website));
        websiteText.setTextAppearance(this, android.R.style.TextAppearance_Small);

        TextView websiteView = new TextView(this);
        websiteView.setText(website);
        websiteView.setTextAppearance(this, android.R.style.TextAppearance_Medium);

        ImageButton browserButton = new ImageButton(this);
        browserButton.setImageResource(R.drawable.browser);
        browserButton.setBackground(null);

        browserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = website;

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        websiteRow.addView(websiteText);
        websiteRow.addView(websiteView);
        websiteRow.addView(browserButton);
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
        shelterAddress.setText(shelter.getAddress());

        TextView shelterMail = (TextView) findViewById(R.id.shelterMail);
        shelterMail.setText(shelter.getMail());

        TextView shelterPhone = (TextView) findViewById(R.id.shelterPhone);
        shelterPhone.setText(shelter.getPhone());

        if (shelter.getWebsite() != null) {
            addWebsiteRow(shelter.getWebsite());
        }

        setShelterFollowing(shelter.isFollowed());
    }

    private void setShelterFollowing(boolean followed) {
        shelter.setFollowed(followed);

        ImageView followingStar = (ImageView) findViewById(R.id.followingStar);
        int starRes = shelter.isFollowed() ? R.drawable.star : 0;
        followingStar.setImageResource(starRes);

        Button followButton = (Button) findViewById(R.id.followShelterButton);
        followButton.setText(shelter.isFollowed() ? getResources().getString(R.string.unfollow) : getResources().getString(R.string.follow));
    }

    private void changeShelterFollowing(boolean followed) {
        HashMap<String, String> request = new HashMap<>();
        request.put("idShelter", Integer.toString(shelter.getIdShelter()));
        request.put("nickname", session.getUserDetails().get("nickname"));

        if (followed) {
            new FollowShelterDbOperation(this).execute(request);
        } else {
            new UnfollowShelterDbOperation(this).execute(request);
        }
    }

    private void addAnimals(HashMap<String, Object> animals) {
        animalsList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(petsGrid, animalsList);
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
        shareDialog.show(FacebookManager.share(title, description, url, null));
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
        String website = shelter.getWebsite() == null ? "" : " - " + shelter.getWebsite();
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
        final ImageButton commentButton = (ImageButton) findViewById(R.id.commentButton);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText commentBox = (EditText) findViewById(R.id.shelterComment);
                String comment = commentBox.getText().toString();

                if (!comment.isEmpty()) {
                    commentShelter();
                } else {
                    Toast.makeText(v.getContext(), getResources().getString(R.string.emptyComment), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialiseShelterComment() {
        onClickCommentButton();
    }

    private void onClickFollowShelter() {
        Button followButton = (Button) findViewById(R.id.followShelterButton);
        followButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeShelterFollowing(!shelter.isFollowed());
            }
        });
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
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.animalsLayout);
        final ImageButton addAnimalButton = new ImageButton(this);
        addAnimalButton.setImageResource(R.drawable.add);
        addAnimalButton.setBackground(null);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(addAnimalButton, params);

        addAnimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAnimalScreen = new Intent(getApplicationContext(), AddAnimalActivity.class);

                addAnimalScreen.putExtra("idShelter", shelter.getIdShelter());
                startActivity(addAnimalScreen);
            }
        });
    }

    private void addSeeShelterMessagesButton() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.shelterLayout);
        final ImageButton seeMessagesButton = new ImageButton(this);
        seeMessagesButton.setImageResource(R.drawable.message);
        seeMessagesButton.setBackground(null);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.topMargin = -27;

        layout.addView(seeMessagesButton, params);

        seeMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messagesScreen = new Intent(getApplicationContext(), ShelterMessagesActivity.class);
                messagesScreen.putExtra("idShelter", shelter.getIdShelter());
                startActivity(messagesScreen);
            }
        });
    }

    private void addSendShelterMessageButton() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.shelterLayout);
        final ImageButton sendMessageButton = new ImageButton(this);
        sendMessageButton.setImageResource(R.drawable.send_message);
        sendMessageButton.setBackground(null);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.topMargin = -27;

        layout.addView(sendMessageButton, params);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendMessageScreen = new Intent(getApplicationContext(), SendMessageActivity.class);
                sendMessageScreen.putExtra("idShelter", shelter.getIdShelter());
                startActivity(sendMessageScreen);
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

    private void onClickSeeAllAdoptedAnimals() {
        final Button seeAllAdoptedAnimalsButton = (Button) findViewById(R.id.seeAllAdoptedAnimalsButton);
        seeAllAdoptedAnimalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent animalsScreen = new Intent(getApplicationContext(), SheltersAdoptedAnimalsActivity.class);
                ParcelableShelter s = (ParcelableShelter) shelter;
                animalsScreen.putExtra("shelter", s);
                startActivity(animalsScreen);
            }
        });
    }

    private void loadSheltersAnimals() {
        HashMap<String, String> animalsRequest = new HashMap<String, String>();
        animalsRequest.put("idShelter", Integer.toString(shelter.getIdShelter()));
        animalsRequest.put("nickname", session.getUserDetails().get("nickname"));
        animalsRequest.put("numberOfAnimals", Integer.toString(3));
        new GetSheltersAnimalsDbOperation(this).execute(animalsRequest);
    }

    private void loadSheltersOpinion() {
        HashMap<String, String> opinionsRequest = new HashMap<String, String>();
        opinionsRequest.put("idShelter", Integer.toString(shelter.getIdShelter()));
        opinionsRequest.put("numberOfOpinions", Integer.toString(3));
        new GetOpinionsAboutShelterDbOperation(this).execute(opinionsRequest);
    }

    private void setOnClickListeners() {
        onClickShareOnFacebook();
        onClickTweet();
        onClickFollowShelter();
        onClickCallShelter();
        onClickSeeAllAnimals();
        onClickSeeAllAdoptedAnimals();
        onClickSeeAllComments();
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

        loadSheltersAnimals();
        loadSheltersOpinion();

        setOnClickListeners();

        addAddAnimalButtonIfShelterAdministrator();
    }
}
