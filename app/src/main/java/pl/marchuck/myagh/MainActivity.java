package pl.marchuck.myagh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.marchuck.myagh.ifaces.FabListener;
import pl.marchuck.myagh.tabs.about_faculty.FacultyFragment;
import pl.marchuck.myagh.tabs.AboutFragment;
import pl.marchuck.myagh.tabs.AghMapFragment;
import pl.marchuck.myagh.tabs.news.NewsFragment;
import pl.marchuck.myagh.tabs.SplashScreenFragment;
import pl.marchuck.myagh.utils.Animations;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static int LOCATION_PERMISSIONS = 61;

    @Bind(R.id.fab)
    public FloatingActionButton fab;
    @Bind(R.id.toolbar)
  public   Toolbar toolbar;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupDrawer();
        replaceTo(SplashScreenFragment.newInstance(), SplashScreenFragment.TAG);
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_map: {
                replaceTo(AghMapFragment.newInstance(), AghMapFragment.TAG);
                break;
            }
            case R.id.nav_news: {
                replaceTo(NewsFragment.newInstance(), NewsFragment.TAG);
                break;
            }
            case R.id.nav_about: {
                replaceTo(AboutFragment.newInstance(), AboutFragment.TAG);
                break;
            }
            case R.id.nav_start: {
                replaceTo(SplashScreenFragment.newInstance(), SplashScreenFragment.TAG);
                break;
            }   case R.id.nav_about_faculty: {
                replaceTo(FacultyFragment.newInstance(), FacultyFragment.TAG);
                break;
            }
            default:
                // showFab();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceTo(android.support.v4.app.Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment, tag)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commitAllowingStateLoss();
        if (fragment instanceof FabListener) {
            FabListener fabListener = ((FabListener) fragment);
            fab.setOnClickListener(fabListener.getFabListener());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: " + "requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == LOCATION_PERMISSIONS) {
            setupMap();
        }
    }

    private void setupMap() {
        Log.i(TAG, "onActivityResult: got it");
        AghMapFragment myFragment = (AghMapFragment) getSupportFragmentManager().findFragmentByTag(AghMapFragment.TAG);
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
            myFragment.setupMap();
        }
    }

    public void hideFab() {
        Log.d(TAG, "hideFab: ");
        Animations.hideView(fab);
    }

    public void showFab() {
        Log.d(TAG, "showFab: ");
        Animations.showView(fab);
    }
}
