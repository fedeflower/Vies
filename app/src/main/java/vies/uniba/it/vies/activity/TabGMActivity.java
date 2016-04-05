package vies.uniba.it.vies.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import vies.uniba.it.vies.R;
import vies.uniba.it.vies.fragment.GalleryFragment;
import vies.uniba.it.vies.fragment.MapFragment;

import java.util.ArrayList;
import java.util.List;

public class TabGMActivity extends AppCompatActivity {
    String test="test";

    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_gm);
        test=getIntent().getStringExtra("album_name");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Vies");
        getSupportActionBar().setSubtitle(test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                int vibrantColor = palette.getVibrantColor(R.color.primary_500);
                int vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_700);
                collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        break;
                    case 1:
                        MapFragment.setClick();
                        break;
                }

                viewPager.setCurrentItem(tab.getPosition());

                /* toast cambio tab
                switch (tab.getPosition()) {
                    case 0:
                        showToast("One");
                        break;
                    case 1:
                        showToast("Two");

                        break;
                    case 2:
                        showToast("Three");

                        break;
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final FloatingActionButton newTravelButton = (FloatingActionButton) findViewById(R.id.addPhoto);
        newTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Caricamento Foto", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Log.d("Comments", Boolean.toString(Prefs.getInstance(getBaseContext()).getBoolean("album2_" + test, false)));
                Prefs.getInstance(getBaseContext()).edit().putBoolean("album2_"+test, true).commit();
                Log.d("Comments", Boolean.toString(Prefs.getInstance(getBaseContext()).getBoolean("album2_" + test, false)));
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                adapter.editFrag(new GalleryFragment(getResources().getColor(R.color.bg), test), "Gallery");
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
                //setupViewPager(viewPager);
                //Intent i = new Intent(getApplicationContext(), NewAlbumActivity.class);
                //startActivity(i);*/
            }
        });

    }

/* funzione show toast
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
*/
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new GalleryFragment(getResources().getColor(R.color.bg), test), "Gallery");
        //adapter.setDettagli(test);
        adapter.addFrag(new MapFragment(test), "Mappa");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<String> dettagli=new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void setDettagli(String dett) {
            dettagli.add(dett);
        }

        public void editFrag(Fragment fragment, String title) {
            mFragmentList.add(0,fragment);
            mFragmentTitleList.add(title);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
