package company.pepisha.find_yours_pets.connection;

import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

public class ServerDbOperation extends AsyncTask<HashMap<String, String>, Void, HashMap<String, Object>> {

    private Context context;

    private String page;

    public ServerDbOperation(Context c, String page) {
        this.context = c;
        this.page = page;
    }

    protected boolean successResponse(HashMap<String, Object> result) {
        return (result != null && result.containsKey("success") && result.get("success").toString().equals("true"));
    }

    @Override
    protected HashMap<String, Object> doInBackground(HashMap<String, String>... params) {
        if (ServerConnectionManager.isConnected(context)) {
            if (params.length > 0) {
                HashMap<String, String> request = params[0];
                request.put("page", page);
                return ServerConnectionManager.sendRequestToServer(params[0]);
            }
        }

        return null;
    }
}