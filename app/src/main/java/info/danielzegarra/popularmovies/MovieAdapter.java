package info.danielzegarra.popularmovies;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by dzegarra on 05/02/17.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ImageView mCover;
        TextView mTitle;
        ViewHolder(View view) {
            super(view);
            context = view.getContext();
            mCover = (ImageView) view.findViewById(R.id.movie_cover_image);
            mTitle = (TextView) view.findViewById(R.id.movie_cover_title);
        }
        void bind(MovieModel model) {
            Picasso.with(context).load(model.poster_url).into(mCover);
            mTitle.setText(model.title);
        }
    }

    private MovieModel[] movies;

    MovieAdapter(MovieModel[] models) {
        movies = models;
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_cover, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel model = movies[position];
        holder.bind(model);
    }

}
