package nanodegree.example.com.popularmoviesapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by parth panchal on 29-02-2016.
 */
public class MoviesListActivity extends AppCompatActivity {
    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String API_KEY = "";//API Key
    private int page_number = 1;
    private int colNo = 2;
    private String FEED_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY + "&page=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        mGridView = (GridView) findViewById(R.id.gridView);

        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_view_item, mGridData);
        mGridView.setAdapter(mGridAdapter);

        new AsyncHttpTask().execute(FEED_URL + "" + page_number);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncHttpTask().execute(FEED_URL + "" + page_number);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        Button columnInc = (Button) findViewById(R.id.col_inc);
        columnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colNo++;
                if (colNo > 4) {
                    colNo = 4;
                }
                mSwipeRefreshLayout.setRefreshing(true);

                mGridView.setNumColumns(colNo);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Button columnDec = (Button) findViewById(R.id.col_dec);
        columnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colNo--;
                if (colNo <= 0) {
                    colNo = 1;
                }
                mSwipeRefreshLayout.setRefreshing(true);

                mGridView.setNumColumns(colNo);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        Button moreMovies = (Button) findViewById(R.id.more_movies);
        moreMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncHttpTask().execute(FEED_URL + "" + page_number);
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                GridItem item = (GridItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);

                intent.putExtra("Movie", item);
                startActivity(intent);
            }
        });
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 1;
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            InputStream stream;
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.connect();


                stream = conn.getInputStream();
                Reader reader;
                reader = new InputStreamReader(stream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(reader);
                parseResult(bufferedReader.readLine());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
                page_number++;
            } else {
                Toast.makeText(MoviesListActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("results");
            GridItem item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("original_title");
                item = new GridItem();
                item.setTitle(title);
                String image = post.optString("poster_path");
                image = "http://image.tmdb.org/t/p/w500/" + image.substring(1);
                item.setImage(image);
                String backdrop = post.optString("backdrop_path");
                backdrop = "http://image.tmdb.org/t/p/w500/" + backdrop.substring(1);
                item.setBackdrop(backdrop);
                int id = post.optInt("id");
                item.setId(id);
                String release_date = post.optString("release_date");
                item.setRelease_date(release_date);
                String adult = post.optString("adult");
                item.setAdult(adult);
                String overview = post.optString("overview");
                item.setOverview(overview);
                String original_language = post.optString("original_language");
                item.setOriginal_language(original_language);
                int vote_count = post.optInt("vote_count");
                item.setVote_count(vote_count);
                double popularity = post.optDouble("popularity");
                item.setPopularity(popularity);
                double vote_average = post.optDouble("vote_average");
                item.setVote_average(vote_average);
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        mSwipeRefreshLayout.setRefreshing(true);

        switch (item.getItemId()) {

            case R.id.sort_by_popolarity:
                FEED_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY + "&page=";
                mGridData.clear();
                page_number = 1;
                new AsyncHttpTask().execute(FEED_URL + "" + page_number);
                break;

            case R.id.sort_by_count:
                FEED_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key=" + API_KEY + "&page=";
                mGridData.clear();
                page_number = 1;
                new AsyncHttpTask().execute(FEED_URL + "" + page_number);
                break;

            case R.id.sort_by_revenue:
                FEED_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=revenue.desc&api_key=" + API_KEY + "&page=";
                mGridData.clear();
                page_number = 1;
                new AsyncHttpTask().execute(FEED_URL + "" + page_number);
                break;

        }
        return true;

    }


}