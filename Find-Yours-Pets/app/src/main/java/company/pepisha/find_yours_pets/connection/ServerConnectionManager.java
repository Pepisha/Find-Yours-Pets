package company.pepisha.find_yours_pets.connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import company.pepisha.find_yours_pets.tools.FileTools;

public class ServerConnectionManager {

    public static final String url = "http://www.find-yours-pets.esy.es/";

    private static final String lineEnd = "\r\n";
    private static final String twoHyphens = "--";
    private static final String boundary =  "*****";

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

    private static HashMap<String, Object> getResponse(HttpURLConnection urlConnection) {
        BufferedReader reader = null;

        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{reader.close();}catch(Exception e){}
        }

        return null;
    }

    public static HashMap<String, Object> sendRequestToServer(HashMap<String, String> request) {
        System.setProperty("http.keepAlive", "false");

        HttpURLConnection urlConnection = connectToServer(url);
        if (urlConnection != null) {
            OutputStreamWriter writer = null;

            try {
                writer = new OutputStreamWriter(urlConnection.getOutputStream());
                String dataToSend = "";

                for (Map.Entry<String, String> entry : request.entrySet()) {
                    dataToSend += encodeData(entry.getKey(), entry.getValue());
                }

                writer.write(dataToSend);
                writer.flush();

                return getResponse(urlConnection);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try{writer.close();}catch(Exception e){}
                try{urlConnection.disconnect();}catch(Exception e){}
            }
        }

        return null;
    }

    private static void writeFileHeader(DataOutputStream out, String id, String value) throws IOException {
        out.writeBytes(twoHyphens + boundary + lineEnd);
        out.writeBytes("Content-Disposition: form-data; name=\"" + id + "\"" + lineEnd);
        out.writeBytes(lineEnd);
        out.writeBytes(value);
        out.writeBytes(lineEnd);
        out.writeBytes(twoHyphens + boundary + lineEnd);
    }

    public static HashMap<String, Object> sendFileToServer(HashMap<String, String> request) {
        HttpURLConnection urlConnection = null;
        FileInputStream fileInputStream = null;
        DataOutputStream imageWriter = null;

        try {
            File fileToUpload = new File(request.remove("filepath"));
            fileInputStream = new FileInputStream(fileToUpload);

            urlConnection = connectToServer(url);
            if (urlConnection != null) {
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                imageWriter = new DataOutputStream(urlConnection.getOutputStream());
                for (Map.Entry<String, String> entry : request.entrySet()) {
                    writeFileHeader(imageWriter, entry.getKey(), entry.getValue());
                }

                imageWriter.writeBytes(twoHyphens + boundary + lineEnd);
                imageWriter.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileToUpload.getName() + "\"" + lineEnd);
                imageWriter.writeBytes(lineEnd);

                Bitmap bitmap = FileTools.fileToBitmap(fileToUpload);
                String extension = FileTools.getFileExtension(fileToUpload);

                if (extension.toLowerCase().equals("jpg")) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, imageWriter);
                } else if (extension.toLowerCase().equals("png")) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, imageWriter);
                }

                imageWriter.writeBytes(lineEnd);
                imageWriter.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                imageWriter.flush();

                return getResponse(urlConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{fileInputStream.close();} catch (IOException e) {}
            try{imageWriter.close();}catch(Exception e){}
            try{urlConnection.disconnect();}catch(Exception e){}
        }

        return null;
    }

    public static Bitmap downloadImage(String filename) {
        HttpURLConnection urlConnection = connectToServer(url + "images/" + filename);
        if (urlConnection != null) {

            try {
                urlConnection.setDoInput(true);
                urlConnection.connect();

                InputStream input = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
