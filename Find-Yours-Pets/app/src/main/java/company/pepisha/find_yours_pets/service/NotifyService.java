package company.pepisha.find_yours_pets.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import company.pepisha.find_yours_pets.AnimalActivity;
import company.pepisha.find_yours_pets.R;

public class NotifyService extends Service {

    private final int UPDATE_INTERVAL = 60 * 1000;
    private final int NOTIFICATION_ID = 1;

    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Service created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onCreate();
        Toast.makeText(this,"Service started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service destroyed", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Création de la notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification") //TODO faire un titre
                        .setContentText("Hello World!"); //TODO faire un contenu

        //Lien vers la vue de l'animal concerné
        Intent animalScreen = new Intent(this, AnimalActivity.class);
        //TODO ajouter le ParcelableAnimal dans l'intent

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

        return super.onStartCommand(intent, flags, startId);
    }


}
