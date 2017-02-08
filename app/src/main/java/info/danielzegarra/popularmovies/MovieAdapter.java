package info.danielzegarra.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MovieModel movie;
        Context context;
        ImageView mCover;
        ViewHolder(View view) {
            super(view);
            context = view.getContext();
            mCover = (ImageView) view.findViewById(R.id.movie_cover_image);
            view.setOnClickListener(this);
        }
        void bind(MovieModel model) {
            movie = model;
            Picasso.with(context).load(movie.getPosterUrl()).into(mCover);
            //mTitle.setText(movie.title);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            context.startActivity(intent);
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
        System.out.println("creando");
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
