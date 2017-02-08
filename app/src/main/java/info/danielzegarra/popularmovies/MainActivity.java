package info.danielzegarra.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView mRecyclerView;
    Spinner mSortOptions;
    Toolbar mToolbar;
    ProgressBar mPbar;
    TextView mInternetError;

    static final int COLUMNS = 2;
    static final String URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";
    static final String URL_RATING = "http://api.themoviedb.org/3/movie/top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPbar = (ProgressBar) findViewById(R.id.progress_bar);
        mSortOptions = (Spinner) findViewById(R.id.sort_options);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_container);
        mInternetError = (TextView) findViewById(R.id.no_internet_error);

        setSupportActionBar(mToolbar);

        //Spinner stuff
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortOptions.setAdapter(dataAdapter);
        mSortOptions.setOnItemSelectedListener(this);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layout = new GridLayoutManager(this, COLUMNS, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);

        loadMovies(true);
    }

    void loadMovies(Boolean popular) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        if (DataService.checkInternetConnection(this)) {
            mPbar.setVisibility(View.VISIBLE);
            mInternetError.setVisibility(View.INVISIBLE);
            new FetchCatalogTask().execute(popular ? URL_POPULAR : URL_RATING);
        } else {
            mPbar.setVisibility(View.INVISIBLE);
            mInternetError.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        loadMovies(position==0);
    }

    @Override
    public void onNothingSelected(AdapterView parent) {}


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
            if (movieModels.length > 0) {
                MovieAdapter adapter = new MovieAdapter(movieModels);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setVisibility(View.VISIBLE);
                mPbar.setVisibility(View.INVISIBLE);
                mInternetError.setVisibility(View.INVISIBLE);
            } else {
                mRecyclerView.setVisibility(View.INVISIBLE);
                mPbar.setVisibility(View.INVISIBLE);
                mInternetError.setVisibility(View.VISIBLE);
            }

        }
    }

}
