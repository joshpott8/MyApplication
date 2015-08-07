package com.example.joshpotterton.recyclerview_example;

//import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mItemTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup NavigationDrawer
        mTitle = mDrawerTitle = getTitle();
        mItemTitles = details.ListItems;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.navitem, mItemTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open , R.string.close){
            @Override
            public void onDrawerClosed(View drawerView) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView){
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(findViewById(R.id.frame_layout) != null){
            menuFragment menu = menuFragment.create(getSupportFragmentManager(), this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout, menu).commit();
        }
    }

    @Override
    public void onBackPressed() {
        this.setTitle("Menu");

        //Attempt to remove tabs bar
        Log.v("App Debug", "Number of tabs: " + Integer.toString(getSupportActionBar().getTabCount()));
        //for(int i = 0; i < getSupportActionBar().getTabCount(); i++){
            //Log.v("App Debug", "Tab Removed: " + Integer.toString(i));
            //getSupportActionBar().removeTabAt(i);
        //}

        //getSupportActionBar().removeAllTabs();
        super.onBackPressed();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        private AppCompatActivity activity;

        public DrawerItemClickListener(AppCompatActivity act){
            activity = act;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            viewPagerFragment frag = viewPagerFragment.create(getSupportFragmentManager(), position, activity);
            Bundle args = new Bundle();
            //args.putInt("position", getLayoutPosition());
            //frag.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, frag);
            transaction.addToBackStack(null);

            transaction.commit();
            mDrawerLayout.closeDrawer(mDrawerList);
        }
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
        //  as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //  noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), settings.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
