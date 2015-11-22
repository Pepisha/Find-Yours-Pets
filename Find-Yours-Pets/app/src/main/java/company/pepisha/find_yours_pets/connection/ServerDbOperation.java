package company.pepisha.find_yours_pets.connection;

import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

public class ServerDbOperation extends AsyncTask<HashMap<String, String>, Void, HashMap<String, String>> {

    private Context context;

    private String page;

    public ServerDbOperation(Context c, String page) {
        this.context = c;
        this.page = page;
    }

    protected boolean successResponse(HashMap<String, String> result) {
        return (result != null && result.containsKey("success") && result.get("success").equals("true"));
    }

    @Override
    protected HashMap<String, String> doInBackground(HashMap<String, String>... params) {
        if (ServerConnectionManager.isConnected(context)) {
            HashMap<String, String> request = params[0];
            request.put("page", page);
            return ServerConnectionManager.sendRequestToServer(params[0]);
        }

        return null;
    }
}