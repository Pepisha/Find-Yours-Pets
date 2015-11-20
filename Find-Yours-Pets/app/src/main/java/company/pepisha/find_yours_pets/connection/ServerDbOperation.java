package company.pepisha.find_yours_pets.connection;

import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

public class ServerDbOperation extends AsyncTask<HashMap<String, String>, Void, HashMap<String, String>> {

    private Context context;

    public ServerDbOperation(Context c) {
        this.context = c;
    }

    @Override
    protected HashMap<String, String> doInBackground(HashMap<String, String>... params) {
        if (ServerConnectionManager.isConnected(context)) {
            return ServerConnectionManager.sendRequestToServer(params[0]);
        }

        return null;
    }
}