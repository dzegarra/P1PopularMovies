package info.danielzegarra.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieModel {
    static String COVER_PATH = "http://image.tmdb.org/t/p/w500/";
    String overview;
    String title;
    String poster_path;
    String poster_url;

    MovieModel(JSONObject data) throws JSONException{
        overview = data.getString("overview");
        title = data.getString("title");
        poster_path = data.getString("poster_path");
        poster_url = buildAbsolutePosterPath(poster_path);
    }

    private String buildAbsolutePosterPath(String relUrl) {
        return COVER_PATH + relUrl;
    }

}
