package company.pepisha.find_yours_pets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Switch;

public class SearchAnimalActivity extends BaseActivity {

    private Switch catsSwitch;
    private Switch dogsSwitch;
    private Switch childrenSwitch;

    private RatingBar catsRatingBar;
    private RatingBar dogsRatingBar;
    private RatingBar childrenRatingBar;

    private void onSwitchClick() {

        catsSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                catsRatingBar.setEnabled(catsSwitch.isChecked());
            }
        });

        dogsSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dogsRatingBar.setEnabled(dogsSwitch.isChecked());
            }
        });

        childrenSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                childrenRatingBar.setEnabled(childrenSwitch.isChecked());
            }
        });
    }

    private void onSearchButtonClick() {
        Button searchButton = (Button) findViewById(R.id.filterSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                if (catsSwitch.isChecked()) {
                    returnIntent.putExtra("catsFriend", catsRatingBar.getRating());
                }

                if (dogsSwitch.isChecked()) {
                    returnIntent.putExtra("dogsFriend", dogsRatingBar.getRating());
                }

                if (childrenSwitch.isChecked()) {
                    returnIntent.putExtra("childrenFriend", childrenRatingBar.getRating());
                }

                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_animal);

        catsSwitch = (Switch) findViewById(R.id.catsSwitch);
        dogsSwitch = (Switch) findViewById(R.id.dogsSwitch);
        childrenSwitch = (Switch) findViewById(R.id.childrenSwitch);

        catsRatingBar = (RatingBar) findViewById(R.id.catsRatingBar);
        dogsRatingBar = (RatingBar) findViewById(R.id.dogsRatingBar);
        childrenRatingBar = (RatingBar) findViewById(R.id.childrenRatingBar);

        onSwitchClick();
        onSearchButtonClick();
    }
}
