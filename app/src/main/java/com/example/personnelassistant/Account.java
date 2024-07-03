package com.example.personnelassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;

public class Account extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView nav;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private Fragment temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this , drawerLayout , toolbar , R.string.start  , R.string.end);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav = findViewById(R.id.navBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout , new HomeFragment()).commit();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_menu :
                        temp = new HomeFragment();
                        break;
                    case R.id.contacts_menu :
                        temp = new ContactFragment();
                        break;
                    case R.id.places_menu :
                        temp = new PlacesFragment();
                        break;
                    case R.id.password_menu :
                        temp = new PasswordFragment();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout , temp).commit();
                return true;
            }
        });

    }
}

