package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.mbms.MbmsErrors;

public class ClientHomepage extends AppCompatActivity {



    private ClientHomepage.SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    String client_name;
    int client_id;
    //ClientMyLaundry.LaundryDetList laundryDetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_homepage);
        client_name = getIntent().getStringExtra("name");
        client_id = getIntent().getIntExtra("id", 0);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*laundryDetList.setClientName(client_name);
        laundryDetList.setClientId(client_id);*/
        mSectionsPagerAdapter = new ClientHomepage.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ClientMyLaundry.newInstance(client_id, client_name);
                case 1:
                    ClientPost clientPost = new ClientPost();
                    clientPost.setClientId(client_id);
                    clientPost.setClientName(client_name);
                    return clientPost;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
