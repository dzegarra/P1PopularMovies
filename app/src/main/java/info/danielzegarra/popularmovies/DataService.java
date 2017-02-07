package info.danielzegarra.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class DataService {

    static final String API_KEY = "7b358d0c7976c773bee5b2831e7c9866";

    static JSONObject loadData(String urlStr) throws IOException, JSONException {
        URL url = buildUrl(urlStr);
        String dataRaw = getResponseFromHttpUrl(url);
        return new JSONObject(dataRaw);
    }

    static URL buildUrl(String url) throws MalformedURLException {
        Uri builtUri = Uri.parse(url).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();
        return new URL(builtUri.toString());
    }

    static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    static MovieModel[] parseResults(JSONObject rawData) throws JSONException {
        JSONArray items = rawData.getJSONArray("results");
        MovieModel[] rows = new MovieModel[items.length()];
        int index = 0;
        while (index < items.length()) {
            rows[index] = new MovieModel(items.getJSONObject(index));
            index++;
        }
        return rows;
    }

}
