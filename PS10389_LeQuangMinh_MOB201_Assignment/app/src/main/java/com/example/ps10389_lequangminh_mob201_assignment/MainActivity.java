package com.example.ps10389_lequangminh_mob201_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ps10389_lequangminh_mob201_assignment.fragment.FragmentCourse;
import com.example.ps10389_lequangminh_mob201_assignment.fragment.FragmentMaps;
import com.example.ps10389_lequangminh_mob201_assignment.fragment.FragmentNews;
import com.example.ps10389_lequangminh_mob201_assignment.fragment.FragmentSocial;
import com.google.android.material.navigation.NavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

    public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.ps10389_lequangminh_mob201_assignment",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
        }
       //
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //
        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentSocial()).commit();
            navigationView.setCheckedItem(R.id.nav_social);
            toolbar.setTitle("Social");
        }}
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_course:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentCourse()).commit();
                toolbar.setTitle("Course");
                break;
            case R.id.nav_maps:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMaps()).commit();
                toolbar.setTitle("Maps");
                break;
            case R.id.nav_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentNews()).commit();
                toolbar.setTitle("News");
                break;
            case R.id.nav_social:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentSocial()).commit();
                toolbar.setTitle("Social");
                break;
            case R.id.nav_caidat:
                Toast.makeText(this,"Cài Đặt",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_thoat:
                finish();
                System.exit(0);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }}

}
