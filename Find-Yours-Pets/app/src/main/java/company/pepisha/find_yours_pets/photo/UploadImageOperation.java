package company.pepisha.find_yours_pets.photo;


import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

import company.pepisha.find_yours_pets.R;
import company.pepisha.find_yours_pets.connection.ServerConnectionManager;
import company.pepisha.find_yours_pets.connection.ServerDbOperation;

public class UploadImageOperation extends ServerDbOperation {

    public UploadImageOperation(Context c) {
        super(c, "uploadImage");
    }

    @Override
    protected HashMap<String, Object> doInBackground(HashMap<String, String>... params) {
        if (ServerConnectionManager.isConnected(context)) {
            if (params.length > 0) {
                HashMap<String, String> request = params[0];
                request.put("page", page);
                return ServerConnectionManager.sendFileToServer(request);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, Object> result) {
        String toastText = (successResponse(result)) ? context.getResources().getString(R.string.uploadSuccess)
                                                        : context.getResources().getString(R.string.uploadFail);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
    }
}
