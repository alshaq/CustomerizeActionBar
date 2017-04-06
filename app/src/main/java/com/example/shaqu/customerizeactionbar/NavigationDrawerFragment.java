package com.example.shaqu.customerizeactionbar;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
// This class is linked to the navigation fragment and will control its behavior
public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME="testpref";

    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private View containerView;

    //this indicate if the user is aware of the drawer existence
    private boolean mUserLearnedDrawer;
    // this indicate if the fragment is started for the first time
    private  boolean mFromSavedInstanceState;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if (savedInstanceState != null){
            mFromSavedInstanceState = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    //This method will set up the navigation bar
    public void setUp(int fragmentId,DrawerLayout drawLayout, Toolbar toolbar) {

        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout = drawLayout;
        // the ActionBarDrawerToggle class controls the in and out of the navigation drawer

        mDrawerToggle= new ActionBarDrawerToggle(getActivity(),
                drawLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    savedToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                //the activity will draw the actionbar again
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //the activity will draw the actionbar again
                getActivity().invalidateOptionsMenu();
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        //this sync the activity with the drawer
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    //this method save the preference , which will be true/false, to a variable we specify
    public void savedToPreferences(Context context, String preferenceName, String perferenceValue){
        //saving the value to a file
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,perferenceValue);
        editor.commit();
    }

    public String readFromPreferences(Context context, String preferenceName, String defaultValue){
        //saving the value to a file
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }
}
