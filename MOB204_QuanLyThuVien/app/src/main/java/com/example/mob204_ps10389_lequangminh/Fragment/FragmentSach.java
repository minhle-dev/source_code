package com.example.mob204_ps10389_lequangminh.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.mob204_ps10389_lequangminh.AdapterViewPager.Adapter_view_sach;
import com.example.mob204_ps10389_lequangminh.R;
import com.google.android.material.tabs.TabLayout;


public class FragmentSach extends Fragment {

    private ViewPager pager;
    private TabLayout tabLayout;
    FragmentManager fragmentManager;
    Adapter_view_sach pagerAdapter;
    private FragmentActivity myContext;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_sach, container,false);
        pager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        addControl();

       return view;
    }

    private void addControl() {
        fragmentManager = myContext.getSupportFragmentManager();
        pagerAdapter = new Adapter_view_sach(fragmentManager);
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

    @Override
    public void onResume() {
        super.onResume();
    }
}




