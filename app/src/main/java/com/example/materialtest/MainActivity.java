package com.example.materialtest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import extra.SortListner;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import myfragment.FragmentBoxOffice;
import myfragment.FragmentSearch;
import myfragment.FragmentUpcoming;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, MaterialTabListener {
    private Toolbar toolbar;
    // private ViewPager mPager;
    //  private SlidingTabLayout mtabs;
    private ViewPager viewPager;
    private MaterialTabHost tabHost;
    Toast toast;
    public static final int MOVIE_SEARCH_RESULTS = 0;
    public static final int MOVIES_HITS = 1;
    public static final int MOVIES_UPCOMING = 2;
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_RATING = "sortRatig";
    private ViewpagerAdapter adapter;

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
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabHost = (MaterialTabHost) findViewById(R.id.tabHost);

        adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }

        });

        for (int i = 0; i < adapter.getCount(); i++) {

            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(adapter.getIcon(i))
                            .setTabListener(this)

            );
        }
        //  mPager= (ViewPager) findViewById(R.id.pager);
        //  mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //mtabs= (SlidingTabLayout) findViewById(R.id.tabs);
        //mtabs.setDistributeEvenly(true);
        //mtabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        //mtabs.setViewPager(mPager);
        setFAB();

    }

    private void setFAB() {


        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_action_new);
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);

        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);

        ImageView iconSortRating = new ImageView(this);
        iconSortRating.setImageResource(R.drawable.ic_action_important);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button));

        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRating = itemBuilder.setContentView(iconSortRating).build();

        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRating.setTag(TAG_SORT_RATING);

        buttonSortName.setOnClickListener(this);
        buttonSortDate.setOnClickListener(this);
        buttonSortRating.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRating)
                .attachTo(actionButton)
                .build();

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
        if (id == R.id.Tab_Using) {

            Intent intent = new Intent(this, TabUsingLiabrary.class);
            startActivity(intent);
        }
        if (id==R.id.video_view){

            Intent intent=new Intent(this,VideoView.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v ){
        Fragment fragment= (Fragment) adapter.instantiateItem(viewPager,viewPager.getCurrentItem());

        if (fragment instanceof SortListner) {

            if (v.getTag().equals(TAG_SORT_NAME)) {

                ((SortListner) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_SORT_DATE)) {

                ((SortListner) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATING)) {

                ((SortListner) fragment).onSortByRating();
            }
        }
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    private class ViewpagerAdapter extends FragmentStatePagerAdapter {
        int[] icons = {R.drawable.search, R.drawable.heart, R.drawable.home};

        public ViewpagerAdapter(FragmentManager fm) {
            super(fm);
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
        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int position) {

            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position){

            return getResources().getDrawable(icons[position]);
        }
    }

    //  class MyPagerAdapter extends FragmentStatePagerAdapter{
    //    int [] icons={R.drawable.search,R.drawable.heart,R.drawable.home};

//        String[] tabText=getResources().getStringArray(R.array.tabs);
//
    //      public MyPagerAdapter(FragmentManager fm) {


    //        super(fm);
    //      tabText=getResources().getStringArray(R.array.tabs);
    //}

    // @Override
    //public Fragment getItem(int position) {
    //  Fragment fragment=null;

    // switch (position){

    //   case MOVIE_SEARCH_RESULTS:

    //  fragment= FragmentSearch.newInstance(" "," ");
    //break;

    //case MOVIES_HITS:

    //fragment= FragmentBoxOffice.newInstance(" "," ");
    //break;

    //case MOVIES_UPCOMING:

    //  fragment= FragmentUpcoming.newInstance(" "," ");
    //break;
    //}
    //return fragment;


    //}

    //@Override
    //public CharSequence getPageTitle(int position) {


    //  Drawable drawable=getResources().getDrawable(icons[position]);
    //drawable.setBounds(0,0,50,50);
    //ImageSpan imageSpan=new ImageSpan(drawable);
    //SpannableString spannableString=new SpannableString("  ");
    //spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    //return spannableString;
    //}

    //@Override
    //public int getCount() {

    //  return 3;
    //}
    //}


}
