package vies.uniba.it.vies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.adapter.OnItemClickListener;
import vies.uniba.it.vies.adapter.TravelAdapter;
import vies.uniba.it.vies.database.DBHelper;
import vies.uniba.it.vies.model.Travel;
import vies.uniba.it.vies.utils.Prefs;
import vies.uniba.it.vies.utils.Utils;
import vies.uniba.it.vies.utils.viesAlert;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemClickListener<Travel> {

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    private List<Travel> travels = new ArrayList<Travel>();
    private TravelAdapter travelAdapter;
    private RecyclerView travelsRecyclerView;
    private Intent introIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));

        introIntent = new Intent(MainActivity.this, TutorialActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

        if (isUserFirstTime){
            DBHelper.getInstance(this).riempiDB();
            startActivity(introIntent);
            }

        relog();
        //run();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setSubtitle("About");
        //getSupportActionBar().setSubtitle(Prefs.getInstance(this).getString("username", "About"));
        travelsRecyclerView = (RecyclerView) findViewById(R.id.travelsRecyclerView);

        travelAdapter = new TravelAdapter(travels, this);

        travelsRecyclerView.setAdapter(travelAdapter);
        travelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final FloatingActionButton newTravelButton = (FloatingActionButton) findViewById(R.id.newTravelButton);
        newTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent i = new Intent(getApplicationContext(), NewAlbumActivity.class);
                startActivity(i);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
		TextView name=(TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_name);
        name.setText("Ciao, ".concat(Prefs.getInstance(this).getString("username", "About")));
        TextView email=(TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_email);
        email.setText(Prefs.getInstance(this).getString("username", "About").concat("@gmail.com"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            viesAlert.exitAlert(this);
        }
    }

    //menù tre puntini
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            /*Intent intent=new Intent(this, SocialActivityVero.class);
            intent.putExtra("album_name","nome_album");
            intent.putExtra("album_location","BARI");
            startActivity(intent);*/
          viesAlert.openAlert(this);
        } else if (id == R.id.nav_trending) {
            startActivity(new Intent(this, TrendingActivity.class));
        } else if (id == R.id.nav_world) {
            startActivity(new Intent(this, WorldActivity.class));
        } else if (id == R.id.nav_tutorial) {
            startActivity(introIntent);
        } else if (id == R.id.nav_about) {
            Log.d("Comments", "About");

            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_settings) {
            viesAlert.openAlert(this);


        } else if (id == R.id.nav_logout) {
            Prefs.getInstance(this).edit().putBoolean("logged_in", false).commit();
            Log.d("Comments", "Slog");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getClass().getCanonicalName(), "onResume() <<CALLED>>");

        setTravelAdapter();

    }

    public void setTravelAdapter() {
        travels = DBHelper.getInstance(getApplicationContext()).getTravels();
        travelAdapter = new TravelAdapter(travels, this);
        travelsRecyclerView.setAdapter(travelAdapter);
    }

    @Override
    public void onItemClick(Travel item) {
        // TODO: define bundle extras to pass the travel object
        //Bundle bundle = new Bundle();
        //bundle.putInt("travelID", item.getId());
        Intent i = new Intent(MainActivity.this, TabGMActivity.class);

        i.putExtra("album_id",item.getId());
        i.putExtra("album_name",item.getName());
		i.putExtra("album_location", item.getLocation().getName());
        Log.w("Comments", "" + item.getDescrizione());
        i.putExtra("descrizione", item.getDescrizione());
        //startActivity(i, bundle);
        startActivity(i);
    }

    /*
    private void run() {
        if (Prefs.getInstance(this).getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            // first time task

            // record the fact that the app has been started at least once
            Prefs.getInstance(this).edit().putBoolean("my_first_time", false).commit();
        }
    }
*/
    private void relog() {
        if (!Prefs.getInstance(this).getBoolean("logged_in", false)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "Relog");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            // first time task
        } else Log.d("Comments", "Already");
    }



}
