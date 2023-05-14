package com.example.tasarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView bottomnavi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bottomnavi = findViewById(R.id.bottomnavi);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new HomeFragment()).commit();

        bottomnavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new HomeFragment()).commit();
                        break;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new SearchFragment()).commit();
                        break;
                    case R.id.person:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new PersonFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}