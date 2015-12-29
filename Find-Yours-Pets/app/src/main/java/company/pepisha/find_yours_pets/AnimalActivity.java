package company.pepisha.find_yours_pets;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.news.News;
import company.pepisha.find_yours_pets.photo.DownloadImage;
import company.pepisha.find_yours_pets.photo.DownloadImageToView;
import company.pepisha.find_yours_pets.socialNetworksManagers.FacebookManager;
import company.pepisha.find_yours_pets.fileExplorer.FileExplorer;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.socialNetworksManagers.TwitterManager;
import company.pepisha.find_yours_pets.tools.FileTools;

public class AnimalActivity extends BaseActivity {

    private static final int PHOTO_CHANGE_REQUEST = 1;

    private Animal animal;
    private File pictureFile = null;
    private ShareDialog shareDialog;
    private int stateButtonId;
    private boolean isUserAdmin = false;

    private GridLayout animalLayout;

    private class ChangeAnimalStatusDbOperation extends ServerDbOperation {
        public ChangeAnimalStatusDbOperation(Context c) {
            super(c, "changeAnimalsStatus");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            String toastText;

            if (successResponse(result)) {
                toastText = getString(R.string.successUpdateAnimalStatus);
                setAnimalState((animal.getState() == 1) ? 2 : 1);
            } else {
                toastText = "Failed status change";
            }

            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
        }
    }

    private class GetAnimalsOwnerDbOperation extends ServerDbOperation {
        public GetAnimalsOwnerDbOperation(Context c) {
            super(c, "getAnimalsOwner");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result.get("nickname") != null) {
                addAnimalsOwner(result.get("nickname").toString());
            }
        }

    }

    private class FollowAnimalDbOperation extends ServerDbOperation {
        public FollowAnimalDbOperation(Context c) {
            super(c, "followAnimal");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                setAnimalFollowing(true);
                Toast.makeText(getApplicationContext(), R.string.followAnimal, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UnfollowAnimalDbOperation extends ServerDbOperation {
        public UnfollowAnimalDbOperation(Context c) {
            super(c, "unfollowAnimal");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (successResponse(result)) {
                setAnimalFollowing(false);
                Toast.makeText(getApplicationContext(), R.string.unfollowAnimal, Toast.LENGTH_SHORT).show();
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
                isUserAdmin = true;
                addUpdateAnimalStateButton();
            }
        }
    }

    private class IsUserAnimalsOwnerDbOperation extends ServerDbOperation {
        public IsUserAnimalsOwnerDbOperation(Context c) {
            super(c, "isUserAnimalsOwner");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result.get("owner").equals(true)) {
                addAddNewButton();
            }
        }
    }

    private class GetLastNewsFromAnimalDbOperation extends ServerDbOperation {
        public GetLastNewsFromAnimalDbOperation(Context c) {
            super(c, "getLastNewsFromAnimal");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if(result.get("news")!=null) {
                News news = new News((JSONObject) result.get("news"));
                addNews(news);
            }
        }
    }

    private class DownloadImageToFile extends DownloadImage {

        private File file;

        public DownloadImageToFile(File f) {
            this.file = f;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                FileTools.bitmapToFile(image, file);
            }
        }
    }

    private void addToGrid(View v, int row, int col) {
        addToGrid(v, row, col, 1, 1);
    }

    private void addToGrid(View v, int row, int col, int rowSpan, int colSpan) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row, rowSpan),
                GridLayout.spec(col, colSpan));

        animalLayout.addView(v, params);
    }

    private void addAnimalsOwner(String ownerNickname) {
        TextView owner = new TextView(this);
        owner.setText(R.string.owner);

        TextView animalsOwner = new TextView(this);
        animalsOwner.setText(ownerNickname);
        animalsOwner.setTextAppearance(this, android.R.style.TextAppearance_Medium);

        addToGrid(owner, 4, 0);
        addToGrid(animalsOwner, 4, 1, 1, 3);
    }

    private void addAddNewButton() {
        Button addNews = new Button(this);
        addNews.setText(R.string.addNews);
        addNews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent addNewsScreen = new Intent(getApplicationContext(), AnimalAddNewsActivity.class);
                addNewsScreen.putExtra("idAnimal", animal.getIdAnimal());
                startActivity(addNewsScreen);
            }
        });

        addToGrid(addNews, 15, 4, 1, 4);
    }

    private void addSeeMoreNewsButton() {
        Button seeMore = new Button(this);
        seeMore.setText(R.string.seeMore);
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent animalNewsScreen = new Intent(getApplicationContext(), AnimalNewsActivity.class);
                animalNewsScreen.putExtra("idAnimal", animal.getIdAnimal());
                startActivity(animalNewsScreen);

            }
        });

        addToGrid(seeMore, 15, 0, 1, 4);
    }

    private void addNews(News news) {
        TextView descriptionView = new TextView(this);
        descriptionView.setText(news.getDescription());
        descriptionView.setMaxWidth(500);

        TextView dateView = new TextView(this);
        dateView.setText(news.getDateNews());
        dateView.setTextColor(Color.GRAY);

        addToGrid(descriptionView, 14, 0, 1, 6);
        addToGrid(dateView, 14, 4);

        addSeeMoreNewsButton();
    }

    private void addUpdateAnimalStateButton() {
        final Button updateStateButton = new Button(this);
        stateButtonId = new AtomicInteger(15).get();
        updateStateButton.setId(stateButtonId);

        addToGrid(updateStateButton, 12, 3, 1, 4);
        setAnimalState(animal.getState());

        updateStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newStatus = animal.getState() == 1 ? 2 : 1;
                changeAnimalState(newStatus);
            }
        });
    }

    private void setAnimalState(int state) {
        animal.setState(state);

        TextView animalState = (TextView) findViewById(R.id.animalState);
        animalState.setText((animal.getState() == Animal.ADOPTION)
                ? getResources().getString(R.string.adoption) : getResources().getString(R.string.adopted));

        Button stateButton = (Button) findViewById(stateButtonId);
        stateButton.setText((animal.getState() == Animal.ADOPTION)
                ? getResources().getString(R.string.adopted) : getResources().getString(R.string.adoption));
    }

    private void changeAnimalState(int state) {
        HashMap<String, String> request = new HashMap<>();
        request.put("idAnimal", Integer.toString(animal.getIdAnimal()));
        request.put("newStatus", Integer.toString(state));

        new ChangeAnimalStatusDbOperation(this).execute(request);
    }

    private void setAnimalFollowing(boolean followed) {
        animal.setFollowed(followed);

        ImageView followingStar = (ImageView) findViewById(R.id.followingStar);
        int starRes = animal.isFollowed() ? R.drawable.star : 0;
        followingStar.setImageResource(starRes);

        Button followButton = (Button) findViewById(R.id.followButton);
        followButton.setText(animal.isFollowed() ? "Unfollow" : "Follow");
    }

    private void changeAnimalFollowing(boolean followed) {
        HashMap<String, String> request = new HashMap<>();
        request.put("idAnimal", Integer.toString(animal.getIdAnimal()));
        request.put("nickname", session.getUserDetails().get("nickname"));

        if (followed) {
            new FollowAnimalDbOperation(this).execute(request);
        } else {
            new UnfollowAnimalDbOperation(this).execute(request);
        }
    }

    private void fillAnimalFields() {
        TextView animalName = (TextView) findViewById(R.id.animalName);
        animalName.setText(animal.getName());

        TextView animalBreed = (TextView) findViewById(R.id.animalBreed);
        animalBreed.setText(animal.getBreed());

        TextView animalAge = (TextView) findViewById(R.id.animalAge);
        animalAge.setText(animal.getAge());

        TextView animalGender = (TextView) findViewById(R.id.animalGender);
        animalGender.setText((animal.getGender() == Animal.Gender.MALE)
                ? getResources().getString(R.string.male) : getResources().getString(R.string.female));

        TextView animalDescription = (TextView) findViewById(R.id.animalDescription);
        animalDescription.setText(animal.getDescription());

        getAndAddAnimalsOwner();

        ImageView animalPicture = (ImageView) findViewById(R.id.animalPicture);
        animalPicture.setImageDrawable(getResources().getDrawable(animal.getDefaultImage()));
        new DownloadImageToView(this, animalPicture.getId()).execute(animal.getPhoto());

        setAnimalFollowing(animal.isFollowed());

        Button followButton = (Button) findViewById(R.id.followButton);
        followButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeAnimalFollowing(!animal.isFollowed());
            }
        });
    }

    private void startPhotoIntent(Intent intent) {
        intent.putExtra("id", animal.getIdAnimal());
        intent.putExtra("description", "");
        startActivityForResult(intent, PHOTO_CHANGE_REQUEST);
    }

    private void photoSelectionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle(R.string.photoSelectionTitle);
        dialog.setContentView(R.layout.photo_selection_layout);

        Button fileExplorerButton = (Button) dialog.findViewById(R.id.fileExplorerButton);
        Button cameraButton = (Button) dialog.findViewById(R.id.cameraButton);

        fileExplorerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startPhotoIntent(new Intent(getApplicationContext(), FileExplorer.class));
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startPhotoIntent(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });

        dialog.show();
    }

    private void onClickChangeAnimalPhoto() {
        Button changeAnimalPhoto = (Button) findViewById(R.id.pictureButton);
        changeAnimalPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photoSelectionDialog();
            }
        });
    }

    private void onClickInterestedOnAnimal() {
        Button interestedButton = (Button) findViewById(R.id.interestedButton);
        interestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView) findViewById(R.id.animalPicture);

                Intent animalMessageScreen = new Intent(v.getContext(), AnimalMessageActivity.class);
                animalMessageScreen.putExtra("animal", (ParcelableAnimal) animal);
                animalMessageScreen.putExtra("photo", ((BitmapDrawable) image.getDrawable()).getBitmap());
                startActivity(animalMessageScreen);
            }
        });
    }

    private void shareOnFacebook() {
        String title = animal.getName();
        String description = animal.getName() + "\n"
                + animal.getBreed()+"\n"
                + animal.getAge()+"\n"
                + animal.getCatsFriend()+"\n"
                + animal.getDogsFriend()+"\n"
                + animal.getChildrenFriend() +"\n"
                + animal.getDescription();
        shareDialog = new ShareDialog(this);
        shareDialog.show(FacebookManager.share(title, description, ServerConnectionManager.url));
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

    private void onClickShareOnTwitter() {
        final Button tweet = (Button) findViewById(R.id.tweetButton);
        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet();
            }
        });
    }

    private void tweet() {
        String description = animal.getName() + "\n"
                + animal.getBreed()+"\n"
                + animal.getAge()+"\n"
                + animal.getCatsFriend()+"\n"
                + animal.getDogsFriend()+"\n"
                + animal.getChildrenFriend() +"\n"
                + animal.getDescription();
        TwitterManager.tweetWithImage(description, Uri.fromFile(pictureFile), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_CHANGE_REQUEST && resultCode == RESULT_OK) {
            ImageView animalPicture = (ImageView) findViewById(R.id.animalPicture);

            File file = new File(data.getStringExtra("photo"));
            animalPicture.setImageBitmap(FileTools.fileToScaledBitmap(file, 195, 195));
        }
    }

    private void addUpdateAnimalStateButtonIfShelterAdministrator() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("idShelter", Integer.toString(animal.getIdShelter()));
        request.put("nickname", session.getUserDetails().get("nickname"));

        new IsShelterAdministratorDbOperation(this).execute(request);
    }

    private void addAnimalsNews() {
        HashMap<String, String> request = new HashMap<>();
        request.put("idAnimal", Integer.toString(animal.getIdAnimal()));

        new GetLastNewsFromAnimalDbOperation(this).execute(request);
    }

    private void addAddNewsButtonIfNeeded() {
        if(isUserAdmin) {
            addAddNewButton();
        } else {
            HashMap<String, String> request = new HashMap<>();
            request.put("idAnimal", Integer.toString(animal.getIdAnimal()));
            request.put("nickname", session.getUserDetails().get("nickname"));

            new IsUserAnimalsOwnerDbOperation(this).execute(request);
        }
    }

    private void getAndAddAnimalsOwner() {
        HashMap<String, String> request = new HashMap<>();
        request.put("idAnimal", Integer.toString(animal.getIdAnimal()));

        new GetAnimalsOwnerDbOperation(this).execute(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        animalLayout = (GridLayout) findViewById(R.id.animalLayout);
        animal = (ParcelableAnimal) getIntent().getParcelableExtra("animal");

        fillAnimalFields();

        onClickChangeAnimalPhoto();
        onClickInterestedOnAnimal();
        onClickShareOnFacebook();
        onClickShareOnTwitter();

        addAddNewsButtonIfNeeded();
        addUpdateAnimalStateButtonIfShelterAdministrator();
        addAnimalsNews();

        File outputDir = getCacheDir();
        File pictureFile = null;
        try {
            pictureFile = File.createTempFile(animal.getName(), "jpg", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new DownloadImageToFile(pictureFile).execute(animal.getPhoto());
    }
}
