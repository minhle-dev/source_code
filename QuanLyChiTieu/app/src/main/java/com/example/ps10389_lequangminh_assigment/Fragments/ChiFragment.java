package com.example.ps10389_lequangminh_assigment.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ps10389_lequangminh_assigment.Adapter.PagerAdapterThu;
import com.example.ps10389_lequangminh_assigment.R;
import com.example.ps10389_lequangminh_assigment.TabPagerFragment.KhoanChiFragment;
import com.example.ps10389_lequangminh_assigment.TabPagerFragment.LoaiChiFragment;

public class ChiFragment extends Fragment {

    private ViewPager pager;
    private TabLayout tabLayout;
    FragmentManager fragmentManager;
    PagerAdapterThu pagerAdapter;
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi, container, false);
        pager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener );

        getFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                new KhoanChiFragment()).commit();
    return view;
    }

    private void addControl() {

        fragmentManager = myContext.getSupportFragmentManager();
        pagerAdapter = new PagerAdapterThu(fragmentManager);
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    pager.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                        case R.id.navBot_khoanChi:
                            selectedFragment = new KhoanChiFragment();
                            break;
                        case R.id.navBot_loaiChi:
                            selectedFragment = new LoaiChiFragment();
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

