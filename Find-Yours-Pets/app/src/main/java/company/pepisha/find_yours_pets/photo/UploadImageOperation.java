package company.pepisha.find_yours_pets.photo;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.connection.ServerConnectionManager;

public class UploadImageOperation extends AsyncTask<String, Void, Boolean> {

    private Context context;

    public UploadImageOperation(Context c) {
        this.context = c;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        if (ServerConnectionManager.isConnected(context)) {
            if (params.length > 0) {
                String filePath = params[0];
                return ServerConnectionManager.sendFileToServer(filePath);
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        String toastText = result ? context.getResources().getString(R.string.uploadSuccess)
                                    : context.getResources().getString(R.string.uploadFail);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
    }
}
