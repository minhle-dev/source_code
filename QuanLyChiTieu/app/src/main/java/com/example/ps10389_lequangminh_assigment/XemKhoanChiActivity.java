package com.example.ps10389_lequangminh_assigment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class XemKhoanChiActivity extends AppCompatActivity {
    TextView ngaychi, sotienchi, loaichi, tenkhoanchi;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_khoan_chi);

        ngaychi = findViewById(R.id.ngaychi);

        sotienchi = findViewById(R.id.sotienchi);
        loaichi = findViewById(R.id.loaichi);
        tenkhoanchi = findViewById(R.id.tenKhoanChi);
        String tenloaichi = getIntent().getStringExtra("loaichi");
        collapsingToolbarLayout = findViewById(R.id.collapsing1);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        toolbar = findViewById(R.id.toolbar_order);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Drawable drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_keyboard_backspace_black_24dp, null);
            //custom màu
            drawable.setColorFilter(ResourcesCompat.getColor(this.getResources(), R.color.colorAccent, null), PorterDuff.Mode.SRC_IN);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(drawable);
        }
        ngaychi.setText(getIntent().getStringExtra("ngaychi"));
        loaichi.setText(tenloaichi);
        sotienchi.setText(getIntent().getStringExtra("sotienchi"));
        tenkhoanchi.setText(getIntent().getStringExtra("tenkhoanchi"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
