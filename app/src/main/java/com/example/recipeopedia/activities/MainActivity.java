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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipeopedia.R;
import com.example.recipeopedia.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private Button btnLogOut;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick logout button");
                ParseUser.logOutInBackground();
                logOutUser();
            }
        });*/

        /*btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick search button");
                goRecipeListActivity();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case R.id.nav_account:
                    Toast.makeText(this, "Account selected", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_settings:
                    Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_logout:
                    Toast.makeText(this, "Log Out item selected", Toast.LENGTH_SHORT).show();
                    logOutUser();
                    return true;
                default: return super.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOutUser() {
        Log.i(TAG, "Attempting to logout user");
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void goRecipeListActivity() {
        Intent i = new Intent(this, RecipeListActivity.class);
        startActivity(i);
        finish();
    }
}