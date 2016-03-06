package nanodegree.example.com.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by parth panchal on 29-02-2016.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    TextView title, rating, overview, release,language,popularity,parentalRating;
    ImageView image, backdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        overview = (TextView) findViewById(R.id.details_overview);
        title = (TextView) findViewById(R.id.details_movie_name);
        rating = (TextView) findViewById(R.id.details_rating);
        image = (ImageView) findViewById(R.id.details_image);
        backdrop = (ImageView) findViewById(R.id.details_backdrop);
        release = (TextView) findViewById(R.id.details_release_date);
        language = (TextView) findViewById(R.id.details_language);
        popularity = (TextView) findViewById(R.id.details_popularity);
        parentalRating = (TextView) findViewById(R.id.details_parentalRating);


        Intent i =getIntent();
        final GridItem item =  i.getParcelableExtra("Movie");

        title.setText(item.getTitle());
        overview.setText(item.getOverview());
        Picasso.with(getApplicationContext()).load(item.getImage()).into(image);
        Picasso.with(getApplicationContext()).load(item.getBackdrop()).into(backdrop);
        String temp_rating = "Rating :\n"+item.getVote_average();
        rating.setText(temp_rating);
        String temp_release = "Release Date : \n"+item.getRelease_date();
        release.setText(temp_release);
        String temp_language = "Original Language :\n"+item.getOriginal_language();
        language.setText(temp_language);
        String temp_popularity = "Popularity : \n"+item.getPopularity()+"%";
        popularity.setText(temp_popularity);
        String temp_parentalRating="Parental Rating : \n";
        if(item.getAdult().equals("true")){
            temp_parentalRating+="A";
        }
        else {
            temp_parentalRating+="U/A";
        }
        parentalRating.setText(temp_parentalRating);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FullScreenImage.class);
                i.putExtra("image", item.getImage());
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

