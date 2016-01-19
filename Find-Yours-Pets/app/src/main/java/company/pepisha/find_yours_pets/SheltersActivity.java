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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.shelter.Shelter;
import company.pepisha.find_yours_pets.parcelable.ParcelableShelter;
import company.pepisha.find_yours_pets.views.ShelterAdapter;

public class SheltersActivity extends BaseActivity implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 1.5F;

    Vibrator vibrator = null;

    private SensorManager sensorManager = null;

    private Sensor accelerometer = null;

    private ListView sheltersList;

    private class GetSheltersDbOperation extends ServerDbOperation {

        public GetSheltersDbOperation(Context c) {
            super(c, "getAllShelters");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null) {
                addShelters(result);
            }
        }
    }

    private Shelter getRandomShelter() {
        if (sheltersList.getCount() > 0) {
            Random r = new Random();
            int i = r.nextInt(sheltersList.getCount());

            if (i < sheltersList.getCount()) {
                return (Shelter) sheltersList.getItemAtPosition(i);
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
            Shelter shelter = getRandomShelter();

            if (shelter != null) {
                vibrator.vibrate(200);

                Intent shelterScreen = new Intent(getApplicationContext(), ShelterActivity.class);
                shelterScreen.putExtra("shelter", (ParcelableShelter) shelter);
                startActivity(shelterScreen);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void addShelters(HashMap<String, Object> shelters) {

        List<Shelter> shelterObjects = new ArrayList<Shelter>();

        for (Map.Entry<String, Object> entry : shelters.entrySet()) {
            ParcelableShelter s = new ParcelableShelter((JSONObject) entry.getValue());
            shelterObjects.add(s);
        }

        ShelterAdapter listAdapter = new ShelterAdapter(this, R.layout.shelter_layout, shelterObjects);
        sheltersList.setAdapter(listAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelters);

        sheltersList = (ListView) findViewById(R.id.sheltersList);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        HashMap<String, String> request = new HashMap<>();
        request.put("nickname", session.getUserDetails().get("nickname"));
        new GetSheltersDbOperation(getApplicationContext()).execute(request);

        sheltersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent shelterScreen = new Intent(getApplicationContext(), ShelterActivity.class);
                ParcelableShelter s = (ParcelableShelter) sheltersList.getItemAtPosition(position);
                shelterScreen.putExtra("shelter", s);
                startActivity(shelterScreen);
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
