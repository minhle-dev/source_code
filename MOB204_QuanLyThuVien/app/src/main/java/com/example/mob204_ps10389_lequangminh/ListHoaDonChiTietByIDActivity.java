package com.example.mob204_ps10389_lequangminh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.mob204_ps10389_lequangminh.Fragment.FragmentHoaDon;
import com.example.mob204_ps10389_lequangminh.adapter.CartAdapter;
import com.example.mob204_ps10389_lequangminh.dao.HoaDonChiTietDAO;
import com.example.mob204_ps10389_lequangminh.model.HoaDonChiTiet;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListHoaDonChiTietByIDActivity extends AppCompatActivity {
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don_chi_tiet_by_id);
        lvCart =  findViewById(R.id.lvHoaDonChiTiet);
        collapsingToolbarLayout = findViewById(R.id.collapsing1);
        toolbar = findViewById(R.id.toolbar_order);
//
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Drawable drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            //custom màu
            drawable.setColorFilter(ResourcesCompat.getColor(this.getResources(), R.color.colorAccent, null), PorterDuff.Mode.SRC_IN);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(drawable);

        }
        setTitle("Hóa đơn chi tiết");
        ///
        hoaDonChiTietDAO = new HoaDonChiTietDAO(ListHoaDonChiTietByIDActivity.this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByID(b.getString("MAHOADON"));
        }
        adapter = new CartAdapter(this, dsHDCT);
        lvCart.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
