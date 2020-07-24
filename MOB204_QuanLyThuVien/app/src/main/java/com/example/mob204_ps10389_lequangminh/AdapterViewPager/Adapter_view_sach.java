package com.example.mob204_ps10389_lequangminh.AdapterViewPager;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mob204_ps10389_lequangminh.TabpagerFragment.FragmentSachTrongKho;
import com.example.mob204_ps10389_lequangminh.TabpagerFragment.FragmentTheLoaiSach;

public class Adapter_view_sach extends FragmentStatePagerAdapter {
    private String listTab[]={"Thể Loại","Sách Trong Kho"};
    private FragmentTheLoaiSach fragmentA;
    private FragmentSachTrongKho fragmentB;
    public Adapter_view_sach(FragmentManager fm) {
        super(fm);
        fragmentA = new FragmentTheLoaiSach();
        fragmentB = new FragmentSachTrongKho();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return fragmentA;
        }else  if (position==1){
            return fragmentB;
        }else{

        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
