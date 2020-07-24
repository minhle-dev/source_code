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

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String> fragmentTitle = new ArrayList<String>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        fragmentList.add(new FragmentAll());
        fragmentList.add(new FragmentSale());
        fragmentList.add(new FragmentFood());
        fragmentList.add(new FragmentAccessories());
        fragmentList.add(new FragmentDrug());
        fragmentTitle.add("Tất cả");
        fragmentTitle.add("Khuyến mãi");
        fragmentTitle.add("Thức ăn");
        fragmentTitle.add("Phụ kiện");
        fragmentTitle.add("Thuốc & Thực phẩm bổ sung");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
