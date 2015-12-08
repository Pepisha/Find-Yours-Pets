package company.pepisha.find_yours_pets.views;


import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import company.pepisha.find_yours_pets.AnimalActivity;
import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;

public class AnimalViews {

    public static void buildGrid(GridLayout grid, final Map<Integer, Animal> animals) {

        for (Map.Entry<Integer, Animal> entry : animals.entrySet()) {
            LinearLayout petLayout = new LinearLayout(grid.getContext());
            petLayout.setOrientation(LinearLayout.VERTICAL);

            ImageButton petPicture = new ImageButton(grid.getContext());
            petPicture.setImageResource(R.drawable.dog);
            petPicture.setId(entry.getKey());

            TextView petName = new TextView(grid.getContext());
            petName.setText(entry.getValue().getName());
            petName.setGravity(Gravity.CENTER);

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
}

