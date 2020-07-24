package com.example.ps10389_lequangminh_assigment.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ps10389_lequangminh_assigment.TabPagerFragment.KhoanChiFragment;
import com.example.ps10389_lequangminh_assigment.TabPagerFragment.LoaiChiFragment;

public class PagerAdapterChi extends FragmentStatePagerAdapter {
    public PagerAdapterChi(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag= new KhoanChiFragment();
                break;
            case 1:
                frag=new LoaiChiFragment();

                break;

        }
        return frag;
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";
        switch (position){
            case 0:
                title = "Khoản Chi";
                break;
            case 1:
                title = "Loại Chi";
                break;
        }
        return title;
    }
}

