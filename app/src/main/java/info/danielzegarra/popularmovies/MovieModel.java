package info.danielzegarra.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

class MovieModel implements Parcelable {
    static final String COVER_PATH = "http://image.tmdb.org/t/p/w185/";

    public int id;
    public String title;
    public String overview;
    public String release_date;
    public String original_title;
    public int vote_count;
    public float popularity;
    public float vote_average;
    public String backdrop_path;
    public String poster_path;


    MovieModel(JSONObject data) throws JSONException{
        id = data.getInt("id");
        title = data.getString("title");
        overview = data.getString("overview");
        release_date = data.getString("release_date");
        original_title = data.getString("original_title");
        vote_count = data.getInt("vote_count");
        popularity = data.getLong("popularity");
        vote_average = data.getLong("vote_average");

        backdrop_path = data.getString("backdrop_path");
        poster_path = data.getString("poster_path");
    }

    String getPosterUrl() {
        return COVER_PATH + this.poster_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.original_title);
        dest.writeInt(this.vote_count);
        dest.writeFloat(this.popularity);
        dest.writeFloat(this.vote_average);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.poster_path);
    }

    protected MovieModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.original_title = in.readString();
        this.vote_count = in.readInt();
        this.popularity = in.readFloat();
        this.vote_average = in.readFloat();
        this.backdrop_path = in.readString();
        this.poster_path = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
