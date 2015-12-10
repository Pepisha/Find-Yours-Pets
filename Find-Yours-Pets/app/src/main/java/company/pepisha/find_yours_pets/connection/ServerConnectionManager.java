package company.pepisha.find_yours_pets.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerConnectionManager {

    public static final String url = "http://www.find-yours-pets.esy.es/";

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
    }

    private static HttpURLConnection connectToServer(String url) {
        HttpURLConnection urlConnection = null;

        try {
            URL urlObject = new URL(url);

            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    private static String encodeData(String id, String value) {
        try {
            return URLEncoder.encode(id, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8") + "&";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HashMap<String, Object> unmarshallReponse(String jsonData) {
        HashMap<String, Object> response = new HashMap<String, Object>();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                Object jsonKey = iterator.next();
                Object val = jsonObject.get(String.valueOf(jsonKey));

                response.put(jsonKey.toString(), val);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static HashMap<String, Object> sendRequestToServer(HashMap<String, String> request) {
        System.setProperty("http.keepAlive", "false");

        HttpURLConnection urlConnection = connectToServer(url);
        if (urlConnection != null) {
            OutputStreamWriter writer = null;
            BufferedReader reader = null;

            try {
                writer = new OutputStreamWriter(urlConnection.getOutputStream());
                String dataToSend = "";

                for (Map.Entry<String, String> entry : request.entrySet()) {
                    dataToSend += encodeData(entry.getKey(), entry.getValue());
                }

                writer.write(dataToSend);
                writer.flush();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String response = "";
                    String line;

                    while ((line = reader.readLine()) != null) {
                        Log.d("================", line);
                        response += line;
                    }

                    return unmarshallReponse(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try{writer.close();}catch(Exception e){}
                try{reader.close();}catch(Exception e){}
                try{urlConnection.disconnect();}catch(Exception e){}
            }
        }

        return null;
    }
}
