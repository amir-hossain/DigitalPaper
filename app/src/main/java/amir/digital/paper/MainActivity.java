package amir.digital.paper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private MenuItem gridOn;
    private MenuItem gridOff;
    private MenuItem visibleListIcon;
    private Fragment fragment;
    private int columnCount = 2;
    private Bundle bundle;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.all);
        fragmentManager=getSupportFragmentManager();
        fragment=new HomeFragment();
        passDataToFragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grid_off:
                showGridOnIcon();
                ((HomeFragment) fragment).onListTypeChange(1);
                columnCount = 1;
                break;
            case R.id.grid_on:
                showGridOffIcon();
                ((HomeFragment) fragment).onListTypeChange(2);
                columnCount = 2;
                break;
            case R.id.about:
                startActivity(new Intent(this,AboutActivity.class));
        }

        return true;

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                showLastVisibleListIcon();
                fragment = new HomeFragment();
                passDataToFragment();
                break;
            case R.id.logout:
                finish();
                break;

        }

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showGridOnIcon() {
        gridOff.setVisible(false);
        gridOn.setVisible(true);
        visibleListIcon = gridOn;
    }

    private void showGridOffIcon() {
        gridOff.setVisible(true);
        gridOn.setVisible(false);
        visibleListIcon = gridOff;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);
        gridOff = menu.findItem(R.id.grid_off);
        gridOn = menu.findItem(R.id.grid_on);
        if (gridOff != null) {
            gridOff.setVisible(true);
            visibleListIcon = gridOff;
        }

        return true;
    }




    private void passDataToFragment() {
        bundle = new Bundle();
        bundle.putInt(StaticDataManager.column_count_key, columnCount);
        fragment.setArguments(bundle);
    }

    private void showLastVisibleListIcon() {
        if (visibleListIcon != null) {
            visibleListIcon.setVisible(true);
        }
    }



}


