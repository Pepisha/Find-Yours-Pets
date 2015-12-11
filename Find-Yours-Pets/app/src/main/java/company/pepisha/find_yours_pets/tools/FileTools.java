package company.pepisha.find_yours_pets.tools;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class FileTools {

    public static Bitmap fileToBitmap(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static String getFileExtension(File file) {
        String filename = file.getName();
        int dot = filename.lastIndexOf('.');
        return filename.substring(dot + 1);
    }
}
