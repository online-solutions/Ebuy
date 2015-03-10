package demo.beta.ebuy.activities;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

import demo.beta.ebuy.R;
import demo.beta.ebuy.tabs.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);


        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        mTabs.setCustomTabView(R.layout.customer_tab_view, R.id.tabText);
        mTabs.setDistributeEvenly(true);

        mTabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));
        mTabs.setViewPager(mPager);
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
            return true;
        }
        if(id == R.id.item_refresh)
            Toast.makeText(getApplicationContext(), "Refresh list", Toast.LENGTH_SHORT).show();
        if(id == R.id.item_search)
            Toast.makeText(getApplicationContext(), "Focus Search Edit Text", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        String[] tabText;
        int[] icons = {R.drawable.ic_search, R.drawable.ic_drinks, R.drawable.ic_dessert};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class MyFragment extends Fragment{
        private TextView textView;
        public static MyFragment getInstance(int position){
            MyFragment myFragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            myFragment.setArguments(bundle);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.my_fragment, container, false);
            textView = (TextView) layout.findViewById(R.id.position);
            Bundle bundle = getArguments();
            if(bundle!=null){
                textView.setText("Page number = " + bundle.getInt("position"));
            }
            return layout;
        }
    }
}
