package com.donor.oncall;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.donor.oncall.fragments.MapViewFragment;
import com.donor.oncall.fragments.MyRequestFragment;


public class BaseActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private static DocSessionManager docSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        docSessionManager = new DocSessionManager(getApplicationContext());
       if(!docSessionManager.isLoggedIn()){
       docSessionManager.checkLogin(BaseActivity.this);
       }
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_share:
                sharingIntent();
                break;
            case R.id.action_logout:
                docSessionManager.logoutUser();
                break;
        }
       return super.onOptionsItemSelected(item);
    }

    public void setUpToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpHomeButton();
    }

    public void setUpHomeButton(){
        final ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    public void sharingIntent(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "'On-demand Blood donation service. Sign-up";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void replaceViewFragment(Fragment fragment, boolean addToBackStack) {
        replaceViewFragment(R.id.container, fragment, addToBackStack);
    }

    protected void replaceViewFragment(int containerResourceId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction =  getSupportFragmentManager()
                .beginTransaction()
                .replace(containerResourceId, fragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }


    public void selectDrawerItem(MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.request_for_blood:
                replaceViewFragment(new MapViewFragment(),true);
                break;
            case R.id.pending_requests:
                replaceViewFragment(new MyRequestFragment(),true);
                break;
            case R.id.donor_feed:
                replaceViewFragment(new MyRequestFragment(),true);
                break;
            case R.id.all_donation_history:
                break;
            case R.id.profile:
                break;

        }
    }
}
