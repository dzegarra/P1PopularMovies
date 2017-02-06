package info.danielzegarra.popularmovies;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    static final int COLUMNS = 2;
    static final String URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_container);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layout = new GridLayoutManager(this, COLUMNS, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);

        MovieAdapter adapter = new MovieAdapter();
        mRecyclerView.setAdapter(adapter);

        try {
            JSONArray results = loadData().getJSONArray("results");
            Toast.makeText(this, "recieved "+results.length()+" movies", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(this, "No results could be found", Toast.LENGTH_LONG).show();
        }
    }

    JSONObject loadData() {
        URL url = buildUrl();
        String dataRaw;
        JSONObject data = null;
        try {
            dataRaw = getResponseFromHttpUrl(url);
            data = new JSONObject(dataRaw);
        } catch (IOException e) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(this, "The response from the server is not JSON", Toast.LENGTH_LONG).show();
        }
        return data;
    }

    URL buildUrl() {
        URL url = null;
        Uri builtUri = Uri.parse(URL_POPULAR).buildUpon()
                .appendQueryParameter("api_key", "7b358d0c7976c773bee5b2831e7c9866")
                .build();
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "The URL can't be builded", Toast.LENGTH_LONG).show();
        }

        return url;
    }

    String getResponseFromHttpUrl(URL url) throws IOException {
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

}
