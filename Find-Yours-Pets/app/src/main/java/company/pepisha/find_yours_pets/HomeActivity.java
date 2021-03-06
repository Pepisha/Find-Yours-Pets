package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.views.AnimalViews;

public class HomeActivity extends BaseActivity implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 1.5F;

    private static final int SEARCH_REQUEST = 1;

    Vibrator vibrator = null;

    private SensorManager sensorManager = null;

    private Sensor accelerometer = null;

    private GridLayout petsFollowedGrid;
    private GridLayout petsSuggestedGrid;
    private GridLayout searchResultGrid;

    private Map<Integer, Animal> followedAnimalsList = new HashMap<>();
    private Map<Integer, Animal> suggestedAnimalsList = new HashMap<>();
    private Map<Integer, Animal> searchResultList = new HashMap<>();

    private class GetHomePageAnimalsDbOperation extends ServerDbOperation {

        public GetHomePageAnimalsDbOperation(Context c) {
            super(c, "getHomePageAnimals");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null && result.get("followedAnimals") != null) {

                HashMap<String, Object> followedAnimals = ServerConnectionManager.unmarshallReponse(result.get("followedAnimals").toString());

                if (result.get("followedAnimals").toString() != "null") {
                    addFollowedAnimals(followedAnimals);
                } else {
                    TextView title = (TextView) findViewById(R.id.petsFollowedText);
                    title.setVisibility(View.INVISIBLE);
                }

                HashMap<String, Object> suggestedAnimals = ServerConnectionManager.unmarshallReponse(result.get("suggestedAnimals").toString());
                addSuggestedAnimals(suggestedAnimals);
            }
        }
    }

    private class GetHomelessAnimalsDbOperation extends ServerDbOperation {

        public GetHomelessAnimalsDbOperation(Context c) {
            super(c, "getHomelessAnimals");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null) {
                addAnimals(result);
            }
        }
    }

    private void addAnimals(HashMap<String,Object> animals) {
        TextView titleFollowed = (TextView) findViewById(R.id.petsFollowedText);
        titleFollowed.setVisibility(View.INVISIBLE);
        GridLayout petsFollowedGrid = (GridLayout) findViewById(R.id.petsFollowedGrid);
        petsFollowedGrid.setVisibility(View.INVISIBLE);

        TextView titleSuggested = (TextView) findViewById(R.id.petsSuggestedText);
        titleSuggested.setVisibility(View.INVISIBLE);
        GridLayout petsSuggestedGrid = (GridLayout) findViewById(R.id.petsSuggestedGrid);
        petsSuggestedGrid.setVisibility(View.INVISIBLE);

        searchResultList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(searchResultGrid, searchResultList);
    }

    private void clear() {
        followedAnimalsList.clear();
        petsFollowedGrid.removeAllViews();
        petsSuggestedGrid.removeAllViews();
    }

    private Animal getRandomAnimal() {
        if (!suggestedAnimalsList.isEmpty()) {
            Random r = new Random();
            Object[] values = suggestedAnimalsList.values().toArray();

            int i = r.nextInt(suggestedAnimalsList.size());

            if (i < suggestedAnimalsList.size()) {
                return (Animal) values[i];
            }
        }

        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
        float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
        float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;
        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            Animal animal = getRandomAnimal();

            if (animal != null) {
                vibrator.vibrate(200);

                Intent animalScreen = new Intent(getApplicationContext(), AnimalActivity.class);
                animalScreen.putExtra("animal", (ParcelableAnimal) animal);
                startActivity(animalScreen);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void addFollowedAnimals(HashMap<String, Object> animals) {
        followedAnimalsList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(petsFollowedGrid, followedAnimalsList);
    }

    private void addSuggestedAnimals(HashMap<String, Object> animals) {
        suggestedAnimalsList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(petsSuggestedGrid, suggestedAnimalsList);
    }

    private void onClickSearchButton() {
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent searchScreen = new Intent(v.getContext(), SearchAnimalActivity.class);
                startActivityForResult(searchScreen, SEARCH_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_REQUEST && resultCode == RESULT_OK) {
            clear();

            int idType = data.getIntExtra("idType", -1);
            float catsFriend = data.getFloatExtra("catsFriend", (float) -1.0);
            float dogsFriend = data.getFloatExtra("dogsFriend", (float) -1.0);
            float childrenFriend = data.getFloatExtra("childrenFriend", (float) -1.0);

            HashMap<String, String> request = new HashMap<String, String>();
            request.put("nickname", session.getUserDetails().get("nickname"));
            if (idType != -1) {
                request.put("idType", Integer.toString(idType));
            }
            if (catsFriend != -1.0) {
                request.put("catsFriend", Float.toString(catsFriend));
            }
            if (dogsFriend != -1.0) {
                request.put("dogsFriend", Float.toString(dogsFriend));
            }
            if (childrenFriend != -1.0) {
                request.put("childrenFriend", Float.toString(childrenFriend));
            }

            new GetHomelessAnimalsDbOperation(this).execute(request);
        }
    }

    private void loadAnimals() {
        HashMap<String, String> request = new HashMap<String, String>();
        request.put("nickname", session.getUserDetails().get("nickname"));
        request.put("numberOfAnimals", Integer.toString(3));
        new GetHomePageAnimalsDbOperation(this).execute(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        petsFollowedGrid = (GridLayout) findViewById(R.id.petsFollowedGrid);
        petsSuggestedGrid = (GridLayout) findViewById(R.id.petsSuggestedGrid);
        searchResultGrid = (GridLayout) findViewById(R.id.searchResultGrid);

        loadAnimals();

        onClickSearchButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
    }
}
