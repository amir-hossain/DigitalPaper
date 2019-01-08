package amir.digital.paper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.adapter.DrawerItemAdapter;
import amir.digital.paper.fragment.AboutFragment;
import amir.digital.paper.fragment.HomeFragment;
import amir.digital.paper.model.DrawerItemModel;

public class MainActivity extends AppCompatActivity {

    private String[] drawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionBar;
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
        drawerItemTitles = getResources().getStringArray(R.array.drawer_item_array);
        drawerList = findViewById(R.id.left_drawer);
        fragmentManager = getSupportFragmentManager();


        setupToolbar();

        DrawerItemModel[] drawerItem = new DrawerItemModel[3];

        drawerItem[0] = new DrawerItemModel(R.drawable.ic_home, "Home");
        drawerItem[1] = new DrawerItemModel(R.drawable.ic_about, "About");
        drawerItem[2] = new DrawerItemModel(R.drawable.ic_exit, "Exit");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemAdapter adapter = new DrawerItemAdapter(this, R.layout.list_view_drawer, drawerItem);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(drawerToggle);
        int color=getResources().getColor(R.color.colorAccent);


        setupDrawerToggle();
        drawerItemSelect(0);
    }


    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawerItemSelect(position);
        }
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
        }

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


    private void drawerItemSelect(int position) {
        switch (position) {
            case 0:
                showLastVisibleListIcon();
                fragment = new HomeFragment();
                passDataToFragment();
                break;
            case 1:
                visibleListIcon.setVisible(false);
                fragment = new AboutFragment();
                break;
            case 2:
                finish();
                break;

            default:
                fragment = new HomeFragment();
                break;
        }


            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            drawerList.setItemChecked(position, true);
            drawerList.setSelection(position);
            setTitle(drawerItemTitles[position]);
            drawerLayout.closeDrawer(drawerList);

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


    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

}
