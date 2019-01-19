package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HandwasherHomepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private HandwasherHomepage.SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handwasher_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(this);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new HandwasherHomepage.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.chat) {
            return true;
        } else if(id == R.id.notification){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.rating) {
           /* Bundle extras = new Bundle();
            extras.putString("client_name",client_name);
            extras.putInt("client_id", client_id);*/
            Intent intent = new Intent(HandwasherHomepage.this, HandwasherRate.class);
            //intent.putExtras(extras);
            startActivity(intent);
        } else if (id == R.id.laundryservices) {
            Intent intent = new Intent(HandwasherHomepage.this, HandwasherLaundryServices.class);
            startActivity(intent);
        } else if (id == R.id.account) {
            //Bundle extras = new Bundle();
            //extras.putString("client_name",client_name);
            //extras.putInt("client_id", client_id);
            Intent intent = new Intent(HandwasherHomepage.this, HandwasherAccount.class);
            //intent.putExtras(extras);
            startActivity(intent);
        } else if (id == R.id.history) {
            Intent intent = new Intent(HandwasherHomepage.this, HandwasherHistory.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            Intent intent = new Intent(HandwasherHomepage.this, HandwasherLogout.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    HandwasherMyLaundry handwasherMyLaundry = new HandwasherMyLaundry();
                    return handwasherMyLaundry;
                case 1:
                    HandwasherBookings handwasherBookings = new HandwasherBookings();
                    return handwasherBookings;
                case 2:
                    HandwasherPosts handwasherPosts = new HandwasherPosts();
                    return handwasherPosts;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
