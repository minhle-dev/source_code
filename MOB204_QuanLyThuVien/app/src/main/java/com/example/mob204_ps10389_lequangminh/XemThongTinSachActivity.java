package com.example.mob204_ps10389_lequangminh;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class XemThongTinSachActivity extends AppCompatActivity {
    TextView tvMasach, tvMaTheLoai, tvTenSach, tvNXB, tvTacGia, tvGiaBia, tvSoLuong;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_thong_tin_sach);
        anhxa();
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


        tvMasach.setText(getIntent().getStringExtra("MASACH"));
        tvMaTheLoai.setText(getIntent().getStringExtra("MATHELOAI"));
        tvTenSach.setText(getIntent().getStringExtra("TENSACH"));
        tvTacGia.setText(getIntent().getStringExtra("TACGIA"));
        tvNXB.setText(getIntent().getStringExtra("NXB"));
        tvSoLuong.setText(getIntent().getStringExtra("SOLUONG"));
        tvGiaBia.setText(getIntent().getStringExtra("GIABIA"));

    }

    public void anhxa() {
        tvMasach = findViewById(R.id.tvMaSachDetail);
        tvMaTheLoai = findViewById(R.id.tvMaTheLoaiDetail);
        tvNXB = findViewById(R.id.tvNXBDetail);
        tvTenSach = findViewById(R.id.tvTenSachDetail);
        tvGiaBia = findViewById(R.id.tvGiaBiaDetail);
        tvTacGia = findViewById(R.id.tvTacGiaDetail);
        tvSoLuong = findViewById(R.id.tvSoLuongDetail);
        collapsingToolbarLayout = findViewById(R.id.collapsing1);
        toolbar = findViewById(R.id.toolbar_order);
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
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
