package com.example.mob204_ps10389_lequangminh.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.TabpagerFragment.FragmentDoanhSo;
import com.example.mob204_ps10389_lequangminh.TabpagerFragment.FragmentTopSach;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class FragmentThongKe extends Fragment {


    private ViewPager pager;
    private TabLayout tabLayout;
    FragmentManager fragmentManager;
    private FragmentActivity myContext;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_thong_ke, container,false);

        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener );

        getFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                new FragmentDoanhSo()).commit();
        return view;


    }
    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.navbot_thongkedoanhso:
                            selectedFragment = new FragmentDoanhSo();
                            break;
                        case R.id.navBot_thongketopsach:
                            selectedFragment= new FragmentTopSach();
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container1,selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onResume() {
        super.onResume();
    }

}
