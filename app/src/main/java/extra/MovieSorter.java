package extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nishant on 17/7/15.
 */
public class MovieSorter {

    public void sortMovieByName(ArrayList<Movies> movies) {
        Collections.sort(movies, new Comparator<Movies>() {
            @Override
            public int compare(Movies lhs, Movies rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public void sortMovieByDate(ArrayList<Movies> movies) {
        Collections.sort(movies, new Comparator<Movies>() {
            @Override
            public int compare(Movies lhs, Movies rhs) {
                return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
            }
        });
    }

    public void sortMovieByRating(ArrayList<Movies> movies){
        Collections.sort(movies, new Comparator<Movies>() {
            @Override
            public int compare(Movies lhs, Movies rhs) {
                int ratingLhs=lhs.getAudienceScore();
                int ratingRhs=rhs.getAudienceScore();
                if (ratingLhs<ratingRhs){
                    return 1;
                }else if (ratingLhs>ratingRhs){
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });
    }
}
