package info.danielzegarra.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView mRecyclerView;
    MenuItem mRating;
    MenuItem mPopular;
    Spinner mSortOptions;
    Toolbar mToolbar;

    static final int COLUMNS = 2;
    static final String URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Spinner stuff
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortOptions = (Spinner) findViewById(R.id.sort_options);
        mSortOptions.setAdapter(dataAdapter);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_container);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layout = new GridLayoutManager(this, COLUMNS, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);

        new FetchCatalogTask().execute(URL_POPULAR);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        
    }

    public class FetchCatalogTask extends AsyncTask<String, Void, MovieModel[]> {

        @Override
        protected MovieModel[] doInBackground(String... params) {
            try {
                JSONObject response = DataService.loadData(params[0]);
                return DataService.parseResults(response);
            } catch (JSONException e) {
                return new MovieModel[0];
            } catch (IOException e) {
                return new MovieModel[0];
            }
        }

        @Override
        protected void onPostExecute(MovieModel[] movieModels) {
            super.onPostExecute(movieModels);
            MovieAdapter adapter = new MovieAdapter(movieModels);
            mRecyclerView.setAdapter(adapter);
        }
    }

}
