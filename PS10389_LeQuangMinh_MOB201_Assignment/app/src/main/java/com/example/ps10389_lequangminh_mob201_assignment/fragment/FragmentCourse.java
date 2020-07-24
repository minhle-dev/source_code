package com.example.ps10389_lequangminh_mob201_assignment.fragment;

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


import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.tabfragment.FragmentLichHoc;
import com.example.ps10389_lequangminh_mob201_assignment.tabfragment.FragmentLichThi;
import com.example.ps10389_lequangminh_mob201_assignment.tabfragment.FragmentLopHoc;
import com.example.ps10389_lequangminh_mob201_assignment.tabfragment.FragmentSinhVien;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FragmentCourse extends Fragment  {

    private FragmentActivity myContext;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_course, container,false);

        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener );

        getFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                new FragmentSinhVien()).commit();

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
                        case R.id.navbot_sinhvien:
                            selectedFragment = new FragmentSinhVien();
                            break;
                        case R.id.navBot_lophoc:
                            selectedFragment= new FragmentLopHoc();
                            break;
                        case R.id.navBot_lichthi:
                            selectedFragment= new FragmentLichThi();
                            break;
                        case R.id.navBot_lichhoc:
                            selectedFragment= new FragmentLichHoc();
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
