package info.danielzegarra.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mVotes;
    private ImageView mCover;
    private RatingBar mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        mTitle = (TextView) findViewById(R.id.movie_detail_title);
        mReleaseDate = (TextView) findViewById(R.id.movie_detail_releasedate);
        mVotes = (TextView) findViewById(R.id.movie_detail_votes);
        mCover = (ImageView) findViewById(R.id.movie_detail_image);
        mRating = (RatingBar) findViewById(R.id.movie_detail_rating);
        mOverview = (TextView) findViewById(R.id.movie_detail_overview);

        setSupportActionBar(mToolbar);

        MovieModel movie = getIntent().getParcelableExtra("movie");
        fillData(movie);
    }

    private void fillData(MovieModel movie) {
        mTitle.setText(movie.title);
        mReleaseDate.setText(movie.release_date);
        mOverview.setText(movie.overview);
        Picasso.with(this).load(movie.getPosterUrl()).into(mCover);
        mVotes.setText(getString(R.string.average_votes) + movie.vote_average);
        mRating.setRating(movie.vote_average / 2);
    }
}
