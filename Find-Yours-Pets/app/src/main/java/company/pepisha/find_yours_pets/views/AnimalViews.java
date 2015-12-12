package company.pepisha.find_yours_pets.views;


import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import company.pepisha.find_yours_pets.AnimalActivity;
import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.photo.DownloadImage;

public class AnimalViews {

    public static void buildGrid(GridLayout grid, final Map<Integer, Animal> animals) {

        for (Map.Entry<Integer, Animal> entry : animals.entrySet()) {
            LinearLayout petLayout = new LinearLayout(grid.getContext());
            petLayout.setOrientation(LinearLayout.VERTICAL);

            final ImageButton petPicture = new ImageButton(grid.getContext());
            petPicture.setImageResource(R.drawable.dog);
            petPicture.setId(entry.getKey());

            TextView petName = new TextView(grid.getContext());
            petName.setText(entry.getValue().getName());
            petName.setGravity(Gravity.CENTER);
            petName.setMaxWidth(195);

            petLayout.addView(petPicture);
            petLayout.addView(petName);

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

            new DownloadImage(activity, animalId).execute(a.getPhoto());
        }

        return animalsList;
    }
}

