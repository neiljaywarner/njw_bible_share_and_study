package com.neiljaywarner.bibleshareandstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    //TODO: OPen drawer on first app open (with shared preferences)


    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        //TODO: Use later for material design goodness.
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(view, menuItem.getTitle(), Snackbar.LENGTH_LONG).show();
                actionBar.setTitle(menuItem.getTitle());
                showSection(menuItem.getOrder());
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        showSection(0); //show first section/study/


    }

    private void showSection(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();


    }
    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.main, menu);


        return super.onCreateOptionsMenu(menu);
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
     * @param filePath - eg Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), which would require external storage permission
     *                 or local files like assets directory
     * @return
     */
    public String loadTextFromFile(String filePath) {
        //stub
        //  http://stackoverflow.com/questions/16110002/read-assets-file-as-string
        //user short code with 50 votes
        //TODO: can put verses in assets directory for good UX with speed and usable in airplane mode/spotty connection.
        return "dummyFileText";
    }

    public String loadTextFromAsset(String assetName) {
        StringBuilder buf = new StringBuilder();
        BufferedReader in = null;
        try (InputStream json = getAssets().open(assetName)) {
            in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
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
        private int mSectionNumber;
        private String mReferencesString;
        public PlaceholderFragment() {
        }

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

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mReferencesString = getReferencesString(mSectionNumber);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.main, menu);

            // Retrieve the share menu item
            MenuItem shareItem = menu.findItem(R.id.menu_share);

            // Now get the ShareActionProvider from the item
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

            shareActionProvider.setShareIntent(getShareIntent(this.getActivity(), getShareText()));

            super.onCreateOptionsMenu(menu, inflater);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textViewVerses = (TextView) rootView.findViewById(R.id.section_label);

            textViewVerses.setText(mReferencesString);
            return rootView;
        }

        /**
         * @return The text to include in the share intent.
         */
        public String getShareText() {
            return mReferencesString.trim();
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

        //for first study https://www.biblegateway.com/passage/?search=Jer%2029:11-13;Matt%207:7-11;Matt%206:25-34;Acts%2017:22-31&interface=print


        /**
         * Returns an {@link android.content.Intent} which can be used to share this item's content with other
         * applications.
         *
         * @param context - Context to be used for fetching resources if needed
         * @return Intent to be given to a ShareActionProvider.
         */
        public Intent getShareIntent(Context context, String text) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            // Get the string resource and bundle it as an intent extra
            intent.putExtra(Intent.EXTRA_TEXT, text);
            return intent;
        }


    }


}
