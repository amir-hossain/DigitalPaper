package amir.digital.paper;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.fragment.HomeFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MenuItem gridOn;
    private MenuItem gridOff;
    private HomeFragment fragment;
    private int columnCount = 2;
    private Bundle bundle;
    private FragmentManager fragmentManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.google_news);

        fragmentManager = getSupportFragmentManager();
        fragment = new HomeFragment();
        passDataToFragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grid_off:
                showGridOnIcon();
                fragment.onListTypeChange(1);
                columnCount = 1;
                break;
            case R.id.grid_on:
                showGridOffIcon();
                fragment.onListTypeChange(2);
                columnCount = 2;
                break;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
            case R.id.logout:
                finish();
                break;
        }

        return true;

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saved:
                
                break;
            case R.id.google_news:
                fragment.showNews(StaticDataManager.google_news);
                break;
            case R.id.bbc_news:
                fragment.showNews(StaticDataManager.bbc_news);

                break;
            case R.id.buzzfeed:
                fragment.showNews(StaticDataManager.buzzfeed);
                break;
            case R.id.mtv_news:
                fragment.showNews(StaticDataManager.mtv_news);
                break;
            case R.id.bbc_sports:
                fragment.showNews(StaticDataManager.bbc_sport);
                break;
            case R.id.espn:
                fragment.showNews(StaticDataManager.espn);
                break;
            case R.id.medical_news:
                fragment.showNews(StaticDataManager.medical_news);
                break;
            case R.id.nat_geo:
                fragment.showNews(StaticDataManager.nat_geo);
                break;
            case R.id.tnw:
                fragment.showNews(StaticDataManager.tnw);
                break;
            case R.id.tech_crunch:
                fragment.showNews(StaticDataManager.techh_crunch);
                break;
            case R.id.ign:
                fragment.showNews(StaticDataManager.ign);
                break;
            case R.id.polygon:
                fragment.showNews(StaticDataManager.polygon);
                break;
        }

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showGridOnIcon() {
        gridOff.setVisible(false);
        gridOn.setVisible(true);

    }

    private void showGridOffIcon() {
        gridOff.setVisible(true);
        gridOn.setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);
        gridOff = menu.findItem(R.id.grid_off);
        gridOn = menu.findItem(R.id.grid_on);
        if (gridOff != null) {
            gridOff.setVisible(true);

        }

        return true;
    }


    private void passDataToFragment() {
        bundle = new Bundle();
        bundle.putInt(StaticDataManager.column_count_key, columnCount);
        fragment.setArguments(bundle);
    }


}


