package ru.xxi_empire.rkhunter.distancebetweentwocities.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by rkhunter on 12/23/16.
 */

public class GeocodeAPI extends AsyncTask<String, Void, LatLng> {
    private static final String TAG = PlaceAPI.class.getSimpleName();

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/geocode";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyBVwq_6X_XjGJOgMjIHeboDiE0LGLlC1xs";

    protected LatLng doInBackground(String... addresses) {
        LatLng result = new LatLng(0, 0);

        HttpsURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try {
            StringBuilder sb = new StringBuilder(
                    PLACES_API_BASE
                            + OUT_JSON
            );

            sb.append("?key=" + API_KEY);
            sb.append("&address=" + URLEncoder.encode(addresses[0], "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpsURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing Places API URL", e);
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            // Log.d(TAG, jsonResults.toString());
        }

        try {
            // Uncomment line below for debugging the request
            // Log.d(TAG, jsonResults.toString());

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
             result = new LatLng(
                     ((JSONArray) jsonObj.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                     ((JSONArray) jsonObj.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng")
             );
        } catch (JSONException e) {
            Log.e(TAG, "Cannot process JSON results", e);
        }

        return result;
    }
}