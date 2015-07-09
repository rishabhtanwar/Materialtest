package com.example.materialtest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import myfragment.FragmentBoxOffice;
import myfragment.FragmentSearch;
import myfragment.FragmentUpcoming;
import myfragment.MyFragment;
import tabs.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mtabs;
    Toast toast;
    public static final int MOVIE_SEARCH_RESULTS=0;
    public static final int MOVIES_HITS=1;
    public static final int MOVIES_UPCOMING=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentnav);
        drawerFragment.setup(R.id.fragmentnav, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
      mPager= (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mtabs= (SlidingTabLayout) findViewById(R.id.tabs);
        mtabs.setDistributeEvenly(true);
        mtabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);
        mtabs.setViewPager(mPager);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "setting is clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.navigate) {
            Intent i = new Intent(this, sub_activity.class);
            startActivity(i);

        }
        if (id==R.id.Tab_Using){

            Intent intent=new Intent(this, TabUsingLiabrary.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
    class MyPagerAdapter extends FragmentStatePagerAdapter{
        int [] icons={R.drawable.search,R.drawable.heart,R.drawable.home};

        String[] tabText=getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {


            super(fm);
            tabText=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
           Fragment fragment=null;

            switch (position){

                case MOVIE_SEARCH_RESULTS:

                    fragment= FragmentSearch.newInstance(" "," ");
                    break;

                case MOVIES_HITS:

                    fragment= FragmentBoxOffice.newInstance(" "," ");
                    break;

                case MOVIES_UPCOMING:

                    fragment= FragmentUpcoming.newInstance(" "," ");
                    break;
            }
            return fragment;


        }

        @Override
        public CharSequence getPageTitle(int position) {


            Drawable drawable=getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,50,50);
            ImageSpan imageSpan=new ImageSpan(drawable);
            SpannableString spannableString=new SpannableString("  ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {

            return 3;
        }
    }


}
