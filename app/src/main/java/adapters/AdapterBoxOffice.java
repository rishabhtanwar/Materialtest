package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.materialtest.R;

import java.util.ArrayList;

import extra.Keys;
import extra.Movies;
import networks.VolleySingleton;

/**
 * Created by nishant on 8/7/15.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {

    private ArrayList<Movies> listMovies = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private VolleySingleton volleySingleton;

    final String TAG = this.getClass().getSimpleName();
    private ImageLoader imageLoader;

    public AdapterBoxOffice(Context context) {

        layoutInflater = LayoutInflater.from(context);

        volleySingleton = VolleySingleton.getInstance();

        imageLoader = volleySingleton.getImageLoader();


    }

    public void setMovieList(ArrayList<Movies> listMovies) {

        this.listMovies = listMovies;
        notifyDataSetChanged();
//        notifyItemRangeChanged(0,listMovies.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.custom_movie_box, parent, false);

        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {

        Movies currentMovies = listMovies.get(position);

        holder.movieTitle.setText(currentMovies.getTitle());

        holder.movieReleaseDate.setText(currentMovies.getReleaseDate().toString());

        holder.movieAudienceScore.setRating(currentMovies.getAudienceScore() / 2.0f);

        final String urlPoster = currentMovies.getUrlPOster();

        Log.e(TAG, urlPoster + "dknsdl");


        if (urlPoster != null) {


            imageLoader.get(urlPoster, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        private ImageView movieThumbnail;

        private TextView movieTitle;

        private TextView movieReleaseDate;

        private RatingBar movieAudienceScore;

        public ViewHolderBoxOffice(View itemView) {

            super(itemView);

            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);

            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);

            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);

            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);


        }

    }

}
