package demo.beta.ebuy.activities;

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
import android.widget.Toast;

import demo.beta.ebuy.R;
import demo.beta.ebuy.fragments.BoxOfficeFragment;
import demo.beta.ebuy.fragments.MyFragment;
import demo.beta.ebuy.fragments.NavigationDrawerFragment;
import demo.beta.ebuy.fragments.SearchFragment;
import demo.beta.ebuy.fragments.UpComingFragment;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener{

    private Toolbar toolbar;
    private ViewPager mPager;
    private MaterialTabHost tabHost;
//    private SlidingTabLayout mTabs;

    public static final int MOVIES_SEARCH_RESULT = 0;
    public static final int MOVIES_HITS = 1;
    public static final int MOVIES_UPCOMING = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        mPager = (ViewPager) findViewById(R.id.pager);
        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
//        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);


        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for(int i = 0; i < adapter.getCount(); i++){
            tabHost.addTab(
                    tabHost.newTab().setIcon(adapter.getIcon(i)).setTabListener(this)
            );
        }
//
//
//        mTabs.setCustomTabView(R.layout.customer_tab_view, R.id.tabText);
//        mTabs.setDistributeEvenly(true);
//
//        mTabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
//        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));
//        mTabs.setViewPager(mPager);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if(id == R.id.item_refresh)
            Toast.makeText(getApplicationContext(), "Refresh list", Toast.LENGTH_SHORT).show();
        if(id == R.id.item_search)
            Toast.makeText(getApplicationContext(), "Focus Search Edit Text", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
//        String[] tabText;
        int[] icons = {R.drawable.ic_food, R.drawable.ic_drinks, R.drawable.ic_dessert};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
//            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            // use for testing
            // show only text "tab = 0,1,2"
//            MyFragment myFragment = MyFragment.getInstance(position);
//            return myFragment;
            Fragment fragment = null;
            switch (position){
                case MOVIES_SEARCH_RESULT:
                    fragment = SearchFragment.newInstance("","");
                    break;
                case MOVIES_HITS:
                    fragment = BoxOfficeFragment.newInstance("","");
                    break;
                case MOVIES_UPCOMING:
                    fragment = UpComingFragment.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
//            Drawable drawable = getResources().getDrawable(icons[position]);
//            drawable.setBounds(0,0,36,36);
//            ImageSpan imageSpan = new ImageSpan(drawable);
//            SpannableString spannableString = new SpannableString(" ");
//            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return spannableString;


        }

        @Override
        public int getCount() {
            return 3;
        }

        private Drawable getIcon(int position){
            return getResources().getDrawable(icons[position]);
        }
    }


}
