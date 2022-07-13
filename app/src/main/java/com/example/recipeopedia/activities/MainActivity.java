package com.example.recipeopedia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.fragments.ProfileFragment;
import com.example.recipeopedia.fragments.RecipeListFragment;
import com.example.recipeopedia.fragments.SavedRecipesFragment;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationView);
        setupDrawerContent(navigationView);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_account:
                fragment = new ProfileFragment();
                changeFragments(menuItem, fragment);
                break;
            case R.id.nav_search:
                fragment = new RecipeListFragment();
                changeFragments(menuItem, fragment);
                break;
            case R.id.nav_saved:
                fragment = new SavedRecipesFragment();
                changeFragments(menuItem, fragment);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Log Out selected", Toast.LENGTH_SHORT).show();
                logOutUser();
                break;
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFragments(MenuItem menuItem, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private void logOutUser() {
        Log.i(TAG, "Attempting to logout user");
        ParseUser.logOutInBackground();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}