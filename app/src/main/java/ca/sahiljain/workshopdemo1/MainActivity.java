package ca.sahiljain.workshopdemo1;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, Response.ErrorListener, Response.Listener<JSONArray> {

    private static final String URL = "http://netflixroulette.net/api/api.php?actor=Nicolas%20Cage";
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new MovieAdapter(this);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_clear) {
            adapter.setData(new ArrayList<Movie>());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        JsonArrayRequest request = new JsonArrayRequest(URL, this, this);
        requestQueue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(swipeRefreshLayout, "Error connecting to server", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        swipeRefreshLayout.setRefreshing(false);
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject obj = response.getJSONObject(i);
                Movie movie = new Movie(obj.getString("show_title"), obj.getString("release_year"));
                movies.add(movie);
            } catch (JSONException e) {
                Snackbar.make(swipeRefreshLayout, "Error parsing JSON", Snackbar.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        adapter.setData(movies);
        Snackbar.make(swipeRefreshLayout, "Got data!", Snackbar.LENGTH_SHORT).show();
    }
}
