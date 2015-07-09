package extra;

import java.util.Date;

/**
 * Created by nishant on 8/7/15.
 */
public class Movies {
    private long id;

    private String title;

    private Date releaseDate;

    private int audienceScore;

    private String urlPOster;

    private String overview;


    public Movies(){

        this.id=id;

        this.title=title;

        this.releaseDate=releaseDate;

        this.audienceScore=audienceScore;

        this.urlPOster=urlPOster;

        this.overview=overview;

    }

    public long getId(){return id;}

    public void setId(long id){this.id=id;}

    public String getTitle(){return title;}

    public void setTitle(String title){this.title=title;}

    public Date getReleaseDate(){return releaseDate;}

    public void setReleaseDate(Date releaseDate){this.releaseDate=releaseDate;}

    public int getAudienceScore(){return audienceScore;}

    public void setAudienceScore(int audienceScore){this.audienceScore=audienceScore;}

    public String getUrlPOster(){return urlPOster;}

    public void setUrlPOster(String urlPOster){this.urlPOster=urlPOster;}

    public String getOverview(){return overview;}

    public void setOverview(String overview){this.overview=overview;}

    @Override
    public String toString() {
        return "ID:"+id+
                "Title"+title+
                "Date "+releaseDate+
                "Vote "+audienceScore+
                "Poster "+urlPOster+
                "Overview "+overview;


    }
}
