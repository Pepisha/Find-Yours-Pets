package company.pepisha.find_yours_pets.photo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class DownloadImageToView extends DownloadImage {

    private Activity activity;

    private int objectId;

    public DownloadImageToView(Activity a, int id) {
        this.activity = a;
        this.objectId = id;
    }

    protected void onPostExecute(Bitmap image) {
        if (image != null) {
            ((ImageView) activity.findViewById(objectId)).setImageBitmap(Bitmap.createScaledBitmap(image, 195, 195, false));
        }
    }
}