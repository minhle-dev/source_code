package com.example.mob204_ps10389_lequangminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.mob204_ps10389_lequangminh.Fragment.FragmentHoaDon;
import com.example.mob204_ps10389_lequangminh.Fragment.FragmentNguoiDung;
import com.example.mob204_ps10389_lequangminh.Fragment.FragmentSach;
import com.example.mob204_ps10389_lequangminh.Fragment.FragmentThongKe;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentThongKe()).commit();
            navigationView.setCheckedItem(R.id.nav_thongke);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_nguoidung:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentNguoiDung()).commit();
                break;
            case R.id.nav_sach:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentSach()).commit();
                break;
            case R.id.nav_hoadon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHoaDon()).commit();
                break;
            case R.id.nav_thongke:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentThongKe()).commit();
                break;
            case R.id.nav_caidat:
                Toast.makeText(this, "Cài Đặt", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dangxuat:
                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                //xoa tinh trang luu tru truoc do
                edit.clear();
                edit.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
