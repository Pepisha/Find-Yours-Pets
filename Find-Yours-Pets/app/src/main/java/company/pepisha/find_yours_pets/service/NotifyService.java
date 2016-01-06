package company.pepisha.find_yours_pets.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import company.pepisha.find_yours_pets.AnimalActivity;
import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;
import company.pepisha.find_yours_pets.db.animal.Animal;
import company.pepisha.find_yours_pets.parcelable.ParcelableAnimal;
import company.pepisha.find_yours_pets.session.SessionManager;

public class NotifyService extends Service {

    private final int UPDATE_INTERVAL = 600 * 1000;
    private final int NOTIFICATION_ID = 1;
    private Timer timer = new Timer();

    public static boolean isActive = false;

    private class GetAnimalCorrespondingToUserPreferences extends ServerDbOperation {
        public GetAnimalCorrespondingToUserPreferences(Context c) {
            super(c, "getAnimalCorrespondingToUserPreferences");
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            if (result != null && !result.get("animal").toString().equals("null")) {
                ParcelableAnimal animal = new ParcelableAnimal((JSONObject)result.get("animal"));
                sendNotification(animal);
            }
        }
    }

    public NotifyService() {
    }

    private void sendNotification(Animal animal) {
        ParcelableAnimal animalToSend = (ParcelableAnimal) animal;

        //Création de la notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Animal matching your preferences")
                        .setContentText(animal.getName()+", "+animal.getBreed()+", "+animal.getAge());

        //Lien vers la vue de l'animal concerné
        Intent animalScreen = new Intent(this, AnimalActivity.class);
        animalScreen.putExtra("animal", animalToSend);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(AnimalActivity.class);
        stackBuilder.addNextIntent(animalScreen);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isActive = true;

        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        askDataToServer();
                    }
                },
                0,
                UPDATE_INTERVAL);
    }

    public void onDestroy() {
        timer.cancel();
        timer = null;
        isActive = false;
    }

    private void askDataToServer() {
        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> request = new HashMap<>();
        request.put("nickname", session.getUserDetails().get("nickname"));

        new GetAnimalCorrespondingToUserPreferences(this).execute(request);
    }
}
