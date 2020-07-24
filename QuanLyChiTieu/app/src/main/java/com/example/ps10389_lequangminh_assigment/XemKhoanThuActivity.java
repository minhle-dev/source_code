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

public class XemKhoanThuActivity extends AppCompatActivity {
    TextView ngay, sotien, loaithu, tenkhoanthu;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_khoan_thu);

        ngay = findViewById(R.id.ngay);

        sotien = findViewById(R.id.sotien);
        loaithu = findViewById(R.id.loaithu);
        tenkhoanthu = findViewById(R.id.tenKhoanThu);
        String Tenloaithu = getIntent().getStringExtra("loaithu");
        collapsingToolbarLayout = findViewById(R.id.collapsing);
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
        ngay.setText(getIntent().getStringExtra("ngay"));

        sotien.setText(getIntent().getStringExtra("sotien"));
        loaithu.setText(Tenloaithu);
        tenkhoanthu.setText(getIntent().getStringExtra("tenkhoanthu"));

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
