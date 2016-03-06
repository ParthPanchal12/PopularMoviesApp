package nanodegree.example.com.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by parth panchal on 29-02-2016.
 */
public class GridItem implements Parcelable {
    private String image;
    private String title;
    private int id;
    private String release_date;
    private String adult;
    private String overview;
    private String backdrop;
    private String original_language;
    private int vote_count;
    private double popularity;
    private double vote_average;

    public static final Parcelable.Creator<GridItem> CREATOR = new Parcelable.Creator<GridItem>() {
        public GridItem createFromParcel(Parcel in) {
            return new GridItem(in);
        }

        public GridItem[] newArray(int size) {
            return new GridItem[size];
        }
    };
    private GridItem (Parcel in) {
        id = in.readInt();
        vote_count = in.readInt();
        image = in.readString();
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        original_language = in.readString();
        popularity = in.readDouble();
        vote_average = in.readDouble();
        backdrop = in.readString();
        adult=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(vote_count);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeString(original_language);
        dest.writeDouble(popularity);
        dest.writeDouble(vote_average);
        dest.writeString(backdrop);
        dest.writeString(adult);
    }
    public GridItem() {
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}