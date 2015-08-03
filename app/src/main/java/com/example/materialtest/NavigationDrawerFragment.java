package com.example.materialtest;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapters.Adapter;
import extra.info;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;

    public static final String PREF_FILE_NAME = "testpref";

    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";

    private  ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerlayout;

    private Boolean mUserlearneddrawer;

    private Boolean mFromsavedinstancestate;

    private  View containerview;

    private Adapter adapter;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserlearneddrawer=Boolean.valueOf(ReadfromPrefrences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mFromsavedinstancestate=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView= (RecyclerView) layout.findViewById(R.id.Drawerlist);
        adapter=new Adapter(getActivity(),getdata());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListner(getActivity(), recyclerView, new ClickListner() {
            @Override
            public void onClick(View view, int position) {

                mDrawerlayout.closeDrawer(GravityCompat.START);

                ((MainActivity)getActivity()).onDrawerItemClicked(position-1);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;

    }

    public static  List<info> getdata(){
        List<info> data=new ArrayList<>();
        int[] icons={R.drawable.ic_action_search_orange, R.drawable.ic_action_trending_orange, R.drawable.ic_action_upcoming_orange};
        String[] titles={"Search","Hits","Upcoming"};
        for(int i=0;i<icons.length && i<titles.length; i++){
            info current=new info();
            current.iconId=icons[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;
    }


    public void setup(int fragmentId,DrawerLayout drawerlayout, final Toolbar toolbar) {

        containerview=getActivity().findViewById(fragmentId);

        mDrawerlayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserlearneddrawer){
                    mUserlearneddrawer=true;
                    saveToPrefrences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserlearneddrawer+"");

                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset<0.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }
        };
        if (mUserlearneddrawer==null && mFromsavedinstancestate==null) {

            mDrawerlayout.openDrawer(containerview);


        }
        mDrawerlayout.setDrawerListener(mDrawerToggle);

        mDrawerlayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPrefrences(Context context, String prefrenceName, String prefrencevalue) {

        SharedPreferences sharedprefrences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedprefrences.edit();

        editor.putString(prefrenceName, prefrencevalue);
        editor.apply();

    }

    public static String ReadfromPrefrences(Context context, String prefrenceName, String defaultvalue) {

        SharedPreferences sharedprefrences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);

        return sharedprefrences.getString(prefrenceName,defaultvalue);

    }

    class RecyclerTouchListner implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;

        private ClickListner clickListner;

        public RecyclerTouchListner(Context context, final RecyclerView recyclerView, final ClickListner clickListner){

            this.clickListner=clickListner;

            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child!=null && clickListner!=null){
                        clickListner.onLongClick(child,recyclerView.getChildPosition(child));


                    }
                }
            });


        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if (child!=null && clickListner!=null && gestureDetector.onTouchEvent(e)){

                clickListner.onClick(child,rv.getChildPosition(child));


            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }

    public static interface ClickListner{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }
}