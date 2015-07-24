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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    private ImageLoader imageLoader;

    private DateFormat dateformatter=new SimpleDateFormat("yyyy-MM-dd");

    public AdapterBoxOffice(Context context) {

        layoutInflater = LayoutInflater.from(context);

        volleySingleton = VolleySingleton.getInstance();

        imageLoader = volleySingleton.getImageLoader();


    }

    public void setMovieList(ArrayList<Movies> listMovies) {

        this.listMovies = listMovies;
        notifyDataSetChanged();

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

        Date movieReleaseDate=currentMovies.getReleaseDate();
        if (movieReleaseDate!=null){

            String formatedDate=dateformatter.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formatedDate);

        }else {

            holder.movieReleaseDate.setText("NA");

        }

        int audienceScore=currentMovies.getAudienceScore();

        if (audienceScore==-1){

            holder.movieAudienceScore.setRating(0.0F);
            holder.movieAudienceScore.setAlpha(0.5F);


        }else {

            holder.movieAudienceScore.setRating(audienceScore/2.0F);
            holder.movieAudienceScore.setAlpha(1.0F);


        }


        final String urlPoster = currentMovies.getUrlPOster();


        if (!urlPoster.equals("NA")) {


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
