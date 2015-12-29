package company.pepisha.find_yours_pets.photo;


import android.graphics.Bitmap;
import android.os.AsyncTask;

import company.pepisha.find_yours_pets.connection.ServerConnectionManager;

public abstract class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    protected Bitmap doInBackground(String... args) {
        String filename = args[0];
        if (filename != null && !filename.isEmpty()) {
            return ServerConnectionManager.downloadImage(args[0]);
        }

        return null;
    }
}