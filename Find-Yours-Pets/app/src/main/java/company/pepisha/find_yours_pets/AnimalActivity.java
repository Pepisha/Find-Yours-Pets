package company.pepisha.find_yours_pets;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.facebook.FacebookManager;
import company.pepisha.find_yours_pets.fileExplorer.FileExplorer;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.photo.DownloadImage;
import company.pepisha.find_yours_pets.tools.FileTools;

public class AnimalActivity extends BaseActivity {

    private static final int PHOTO_CHANGE_REQUEST = 1;

    private Animal animal;
    private ShareDialog shareDialog;
    private int stateButtonId;

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
                addUpdateAnimalStateButton();
            }
        }
    }

    private void addUpdateAnimalStateButton() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        final Button updateStateButton = new Button(this);
        stateButtonId = new AtomicInteger(15).get();
        updateStateButton.setId(stateButtonId);
        layout.addView(updateStateButton);
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

        ImageView animalPicture = (ImageView) findViewById(R.id.animalPicture);
        animalPicture.setImageDrawable(getResources().getDrawable(R.drawable.dog));
        new DownloadImage(this, animalPicture.getId()).execute(animal.getPhoto());

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        animal = (ParcelableAnimal) getIntent().getParcelableExtra("animal");

        fillAnimalFields();
        onClickChangeAnimalPhoto();
        onClickShareOnFacebook();
        addUpdateAnimalStateButtonIfShelterAdministrator();
    }
}
