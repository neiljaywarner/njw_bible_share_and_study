package com.neiljaywarner.bibleshareandstudy;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
         {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    private CharSequence mTitle;

             private DrawerLayout mDrawerLayout;

             @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
               // Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                Snackbar.make(view, menuItem.getTitle(), Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }
/*
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }
    */






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textViewVerses = (TextView) rootView.findViewById(R.id.section_label);
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            String referencesString = getReferencesString(sectionNumber);
            textViewVerses.setText(referencesString);
            return rootView;
        }

        private String getReferencesString(int sectionNumber) {
            String[] referencesArray;
            //TODO: Refactor into hashmaps/json etc, this is prototyping.
            switch (sectionNumber - 1) {
                case 0:
                    referencesArray = getResources().getStringArray(R.array.study_1_verses);
                    break;
                case 1:
                    referencesArray = getResources().getStringArray(R.array.study_2_verses);
                    break;

                case 2:
                    referencesArray = getResources().getStringArray(R.array.study_3_verses);
                    break;
                default:
                    referencesArray = getResources().getStringArray(R.array.study_1_verses);
                    break;


            }
            String references = "";
            for (String reference : referencesArray) {
                references += "\n\n" + reference;
            }
            return references;
        }



        private String getSectionLabel() {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            return getResources().getStringArray(R.array.section_labels)[sectionNumber-1];

        }
        //TODO: Separate fragment.
    }



}
