package com.jayway.mykanbanboard.app;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jayway.mykanbanboard.app.model.Item;
import com.jayway.mykanbanboard.app.model.Model;
import com.jayway.mykanbanboard.app.tasks.MoveFormPoster;
import com.jayway.mykanbanboard.app.view.ItemView;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, MyKanbanApplication.ModelUpdateListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyKanbanApplication)getApplication()).addModelUpdateListener(this);
        initiateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyKanbanApplication)getApplication()).removeModelUpdateListener(this);
    }

    private void initiateUI(){
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // Check which tab (if any) i selected, then remove any tabs and add one for each section.
        int selectedTab = actionBar.getSelectedNavigationIndex();
        CharSequence selectedTabTitle = mSectionsPagerAdapter.getPageTitle(selectedTab < 0 ? 0 : selectedTab);
        actionBar.removeAllTabs();
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            CharSequence title = mSectionsPagerAdapter.getPageTitle(i);
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(title)
                            .setTabListener(this));
            // Select this tab if it matches the previously selected one
            if(title.equals(selectedTabTitle)){
                actionBar.setSelectedNavigationItem(i);
            }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onModelUpdated(Model model) {
        initiateUI();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Model model;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            model = ((MyKanbanApplication)getApplication()).getModel();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return model.getColumns().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < model.getColumns().size()){
                return model.getColumn(position).getName();
            } else {
                return null;
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements MoveFormPoster.Callback{

        private static final String COLUMN_NUMBER = "columnNbr";
        private int columnNumber;

        /**
         * Returns a new instance of this fragment for the given items.
         */
        public static PlaceholderFragment newInstance(int columnNumber) {
            PlaceholderFragment f = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(COLUMN_NUMBER, columnNumber);
            f.setArguments(args);
            return f;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            columnNumber = getArguments().getInt(COLUMN_NUMBER);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            LinearLayout columnView = (LinearLayout) rootView.findViewById(R.id.container);

            MyKanbanApplication app = (MyKanbanApplication)getActivity().getApplication();
            List<Item> items = app.getModel().getColumn(columnNumber).getItems();

            for (Item item : items){
                ItemView itemView = new ItemView(getActivity(), item);
                for(final Item.Move move : item.getMoves()){
                    Button button = new Button(getActivity());
                    button.setText(move.getTitle());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new MoveFormPoster(PlaceholderFragment.this).execute(move);
                        }
                    });
                    itemView.addMoveButton(button);
                }
                columnView.addView(itemView);
            }

            return rootView;
        }

        @Override
        public void onMoveFormPosterComplete() {
            ((MyKanbanApplication)getActivity().getApplication()).updateModel();
        }
    }

}
