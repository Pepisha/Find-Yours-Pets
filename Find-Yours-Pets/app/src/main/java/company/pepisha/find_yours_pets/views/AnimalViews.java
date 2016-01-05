package company.pepisha.find_yours_pets.views;


import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import company.pepisha.find_yours_pets.AnimalActivity;
import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalType;
import company.pepisha.find_yours_pets.db.animal.animalType.AnimalTypeOperation;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.photo.DownloadImageToView;
import company.pepisha.find_yours_pets.session.SessionManager;

public class AnimalViews {

    public static void buildGrid(GridLayout grid, final Map<Integer, Animal> animals) {

        for (Map.Entry<Integer, Animal> entry : animals.entrySet()) {
            RelativeLayout petLayout = new RelativeLayout(grid.getContext());

            final ImageButton petPicture = new ImageButton(grid.getContext());
            petPicture.setImageResource(entry.getValue().getDefaultImage());
            petPicture.setId(entry.getKey());
            petPicture.setMinimumHeight(250);
            petPicture.setBackground(null);
            petPicture.setPadding(30, 0, 30, 0);

            TextView petName = new TextView(grid.getContext());
            petName.setText(entry.getValue().getName());
            petName.setGravity(Gravity.CENTER);
            petName.setMaxWidth(195);

            ImageView star = new ImageView(grid.getContext());
            if (entry.getValue().isFollowed()) {
                star.setImageResource(R.drawable.star);
            }

            ImageView favorite = new ImageView(grid.getContext());
            if (entry.getValue().isFavorite()) {
                favorite.setImageResource(R.drawable.favorite);
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.topMargin = 5;

            petLayout.addView(petPicture, params);

            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, petPicture.getId());
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.topMargin = 5;
            petLayout.addView(petName, params);

            params = new RelativeLayout.LayoutParams(60, 60);
            petLayout.addView(star, params);

            params = new RelativeLayout.LayoutParams(60, 60);
            params.addRule(RelativeLayout.ALIGN_RIGHT, petPicture.getId());
            params.topMargin = 3;
            petLayout.addView(favorite, params);

            grid.addView(petLayout);

            petPicture.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent animalScreen = new Intent(v.getContext(), AnimalActivity.class);
                    animalScreen.putExtra("animal", (ParcelableAnimal) animals.get(v.getId()));

                    v.getContext().startActivity(animalScreen);
                }
            });
        }
    }

    public static Map<Integer, Animal> getAnimalsList(Activity activity, Map<String, Object> animals) {
        Map<Integer, Animal> animalsList = new HashMap<>();

        for (Map.Entry<String, Object> entry : animals.entrySet()) {
            int animalId = Integer.parseInt(entry.getKey());
            ParcelableAnimal a = new ParcelableAnimal((JSONObject) entry.getValue());
            animalsList.put(animalId, a);

            new DownloadImageToView(activity, animalId).execute(a.getPhoto());
        }

        return animalsList;
    }

    public static void createTypesRadioButtons(RadioGroup radioGroup) {
        AnimalTypeOperation animalTypeDbOperation = new AnimalTypeOperation(radioGroup.getContext());
        animalTypeDbOperation.open();
        List<AnimalType> animalTypes = animalTypeDbOperation.getAllAnimalTypes();
        animalTypeDbOperation.close();

        for (AnimalType type : animalTypes) {
            RadioButton rb = new RadioButton(radioGroup.getContext());
            rb.setText(type.getName());
            rb.setId(type.getIdAnimalType());
            radioGroup.addView(rb);
        }
    }
}

