package petshop.minhle.asmandroidnetworking.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import petshop.minhle.asmandroidnetworking.View.Fragment.FragmentAccessories;
import petshop.minhle.asmandroidnetworking.View.Fragment.FragmentAll;
import petshop.minhle.asmandroidnetworking.View.Fragment.FragmentDrug;
import petshop.minhle.asmandroidnetworking.View.Fragment.FragmentFood;
import petshop.minhle.asmandroidnetworking.View.Fragment.FragmentSale;
import petshop.minhle.asmandroidnetworking.View.Login.Fragment.FragmentLogin;
import petshop.minhle.asmandroidnetworking.View.Login.Fragment.FragmentRegister;

public class ViewPagerAdapterLogin extends FragmentPagerAdapter {


    public ViewPagerAdapterLogin(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentLogin fragmentLogin = new FragmentLogin();
                return fragmentLogin;
            case 1:
                FragmentRegister fragmentRegister = new FragmentRegister();
                return fragmentRegister;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Đăng Nhập";
            case 1:
                return "Đăng Ký";
            default:
                return null;
        }
    }
}
