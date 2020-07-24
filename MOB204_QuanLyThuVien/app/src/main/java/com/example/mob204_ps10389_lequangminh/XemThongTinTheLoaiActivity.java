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
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class XemThongTinTheLoaiActivity extends AppCompatActivity {
    TextView tvMaTheLoai, tvTenTheLoai, tvViTri, tvMoTa;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_thong_tin_the_loai);
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

        tvMaTheLoai.setText(getIntent().getStringExtra("MATHELOAI"));
        tvTenTheLoai.setText(getIntent().getStringExtra("TENTHELOAI"));
        tvViTri.setText(getIntent().getStringExtra("VITRI"));
        tvMoTa.setText(getIntent().getStringExtra("MOTA"));

    }
    public void anhxa(){
        tvMaTheLoai = findViewById(R.id.tvMaTheLoai);
        tvTenTheLoai = findViewById(R.id.tvTenTheLoai);
        tvViTri = findViewById(R.id.tvViTri);
        tvMoTa = findViewById(R.id.tvMoTa);
        collapsingToolbarLayout = findViewById(R.id.collapsing1);
        toolbar = findViewById(R.id.toolbar_order);
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
