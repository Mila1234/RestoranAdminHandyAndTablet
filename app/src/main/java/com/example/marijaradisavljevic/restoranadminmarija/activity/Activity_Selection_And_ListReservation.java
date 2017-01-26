package com.example.marijaradisavljevic.restoranadminmarija.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marijaradisavljevic.restoranadminmarija.R;
import com.example.marijaradisavljevic.restoranadminmarija.adapters.Content;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FragmentListReservations;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FragmentSelection;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Add_Menu_Item;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_List_Rezer_and_Selection;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_Log_Out;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.Fragment_User_Info;
import com.example.marijaradisavljevic.restoranadminmarija.fragments.FreagmentAddOrder;
import com.example.marijaradisavljevic.restoranadminmarija.servis.Servis;

import java.util.List;


/**
 * Created by marija.radisavljevic on 5/12/2016.
 */
public class Activity_Selection_And_ListReservation extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_listrezerv_main);//activity_gui_layout

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationContentDescription(getResources().getString(R.string.nameOfApp));
        //toolbar.setLogo(R.drawable.help);
        toolbar.setLogoDescription(getResources().getString(R.string.Logo_description));
        toolbar.setSubtitle(Servis.getInstance().toolBarTypeNameSurnameString());


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            View recyclerView = findViewById(R.id.item_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);

            Bundle arguments = new Bundle();
            //arguments.putString(Fragment_List_Rezer_and_Selection.ARG_ITEM_ID, holder.mItem.id);
            Fragment_List_Rezer_and_Selection fragment4 = new Fragment_List_Rezer_and_Selection();
            fragment4.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment4).commit();

            mTwoPane = true;
        }else {
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container_gui);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //...anything you may need to do to handle pager state...
                    mSectionsPagerAdapter.notifyDataSetChanged(); //this line will force all pages to be loaded fresh when changing between fragments
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new Activity_Selection_And_ListReservation.SimpleItemRecyclerViewAdapter(Content.ITEMSKONOBAR));
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<Activity_Selection_And_ListReservation.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Content.MainItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<Content.MainItem> items) {
            mValues = items;
        }

        @Override
        public Activity_Selection_And_ListReservation.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new Activity_Selection_And_ListReservation.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Activity_Selection_And_ListReservation.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            // holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        switch (Integer.parseInt(holder.mItem.id)) {
                            case 1:
                                arguments.putString(Fragment_User_Info.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_User_Info fragment1 = new Fragment_User_Info();
                                fragment1.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment1).commit();

                                break;
                            case 2:
                                arguments.putString(Fragment_List_Rezer_and_Selection.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_List_Rezer_and_Selection fragment4 = new Fragment_List_Rezer_and_Selection();
                                fragment4.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment4).commit();

                                break;
                            case 3:
                                arguments.putString(FreagmentAddOrder.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_Add_Menu_Item fragment5 = new Fragment_Add_Menu_Item();
                                fragment5.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment5).commit();

                                break;
                            case 4:
                                arguments.putString(Fragment_Log_Out.ARG_ITEM_ID, holder.mItem.id);
                                Fragment_Log_Out fragment7 = new Fragment_Log_Out();
                                fragment7.setArguments(arguments);
                                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment7).commit();

                                break;
                            case 5:

                                break;
                            case 6:

                                break;
                            case 7:

                                break;
                        }
                    } else {
                        // za pocetak da napravim da se tablet portartai izgleda kao handy app
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            //public final TextView mIdView;
            public final TextView mContentView;
            public Content.MainItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                // mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manu_gui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_user_info:
                Intent intent = new Intent(getApplicationContext(), ActivityHost.class);
                intent.putExtra("name","FragmentUserInfo");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                return true;
            case R.id.action_logout:
                //call popup win for logout
                intent = new Intent(getApplicationContext(), ActivityHost.class);
                intent.putExtra("name","FreagmentLogOut");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                return true;
            case R.id.action_add://plus action

                Intent intent2 = new Intent(getApplicationContext(), ActivityHost.class);
                intent2.putExtra("name","FreagmentAddOrder");
                intent2.putExtra("action","plusbutton");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_placeholder_layout, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new FragmentListReservations();

                case 1:
                    return new FragmentSelection();
                default:
                    return PlaceholderFragment.newInstance(position);
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }


}
