package company.pepisha.find_yours_pets.photo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    private Activity activity;

    private int objectId;

    public DownloadImage(Activity a, int id) {
        this.activity = a;
        this.objectId = id;
    }

    protected Bitmap doInBackground(String... args) {
        String filename = args[0];
        if (filename != null && !filename.isEmpty()) {
            return ServerConnectionManager.downloadImage(args[0]);
        }

        return null;
    }

    protected void onPostExecute(Bitmap image) {
        if (image != null) {
            ((ImageView) activity.findViewById(objectId)).setImageBitmap(Bitmap.createScaledBitmap(image, 195, 195, false));
        }
    }
}