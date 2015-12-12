package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.views.AnimalViews;

public class HomeActivity extends BaseActivity implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 1.5F;

    private SensorManager sensorManager = null;

    private Sensor accelerometer = null;

    private GridLayout petsGrid;

    private Map<Integer, Animal> animalsList = new HashMap<>();

    private class GetAnimalsDbOperation extends ServerDbOperation {

        public GetAnimalsDbOperation(Context c) {
            super(c, "getHomelessAnimals");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null) {
                addAnimals(result);
            }
        }
    }

    private Animal getRandomAnimal() {
        if (!animalsList.isEmpty()) {
            Random r = new Random();
            int i = r.nextInt(animalsList.size());

            if (i < animalsList.size()) {
                return animalsList.get(i);
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
                Intent animalScreen = new Intent(getApplicationContext(), AnimalActivity.class);
                animalScreen.putExtra("animal", (ParcelableAnimal) animal);
                startActivity(animalScreen);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void addAnimals(HashMap<String, Object> animals) {
        animalsList = AnimalViews.getAnimalsList(this, animals);
        AnimalViews.buildGrid(petsGrid, animalsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        petsGrid = (GridLayout) findViewById(R.id.petsGrid);
        new GetAnimalsDbOperation(getApplicationContext()).execute(new HashMap<String, String>());

        Button addAnimalButton = (Button) findViewById(R.id.button);

        addAnimalButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent addAnimalScreen = new Intent(getApplicationContext(), AddAnimalActivity.class);
                startActivity(addAnimalScreen);
            }
        });
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
