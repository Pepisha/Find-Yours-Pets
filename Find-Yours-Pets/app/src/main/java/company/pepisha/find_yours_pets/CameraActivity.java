package company.pepisha.find_yours_pets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import company.pepisha.find_yours_pets.camera.CameraPreview;
import company.pepisha.find_yours_pets.photo.PhotoConfirmationWindow;
import company.pepisha.find_yours_pets.photo.UploadImageOperation;
import company.pepisha.find_yours_pets.tools.FileTools;
import company.pepisha.find_yours_pets.views.ErrorView;

public class CameraActivity extends BaseActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    final Context context = this;
    private File currentPicture = null;

    private int targetId;
    private String photoDescription;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d("TAG", "Error creating media file, check storage permissions: "
                );
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);

                Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                ExifInterface exif = new ExifInterface(pictureFile.toString());

                if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")) {
                    realImage= FileTools.rotateImage(realImage, 90);
                } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")) {
                    realImage= FileTools.rotateImage(realImage, 270);
                } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")) {
                    realImage= FileTools.rotateImage(realImage, 180);
                } else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")) {
                    realImage= FileTools.rotateImage(realImage, 90);
                }

                realImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();

                Toast.makeText(getApplicationContext(),"Picture saved : " + pictureFile.getPath(), Toast.LENGTH_LONG).show();

                currentPicture = pictureFile;

                Log.d("FILEPATH",pictureFile.getPath() );
            } catch (FileNotFoundException e) {
                Log.d("TAG", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("TAG", "Error accessing file: " + e.getMessage());
            }
        }
    };

    private void onClickButtonCapture(){
        ImageButton captureButton = (ImageButton) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                        final PhotoConfirmationWindow alertDialog = new PhotoConfirmationWindow(context);

                        alertDialog.setNegativeClickListener(new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alertDialog.cancel();
                                mPreview.refresh();
                            }
                        });

                        alertDialog.setPositiveClickListener(new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (currentPicture != null) {
                                    HashMap<String, String> request = new HashMap<String, String>();
                                    request.put("filepath", currentPicture.getAbsolutePath());
                                    request.put("targetId", Integer.toString(targetId));
                                    request.put("description", photoDescription);
                                    new UploadImageOperation(getApplicationContext()).execute(request);

                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("photo", currentPicture.getAbsolutePath());
                                    setResult(RESULT_OK, returnIntent);
                                } else {
                                    setResult(RESULT_CANCELED);
                                }

                                finish();
                            }
                        });

                        alertDialog.show();
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera(){
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkCameraHardware(context)) {
            ErrorView.errorPage(this, "Pas d'appareil photo !");
            return;
        }

        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance();
        if (mCamera == null) {
            ErrorView.errorPage(this, "Appareil photo non disponible");
            return;
        }

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        Intent i = getIntent();
        targetId = i.getIntExtra("id", 0);
        photoDescription = i.getStringExtra("description");

        onClickButtonCapture();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c;
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }



    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
