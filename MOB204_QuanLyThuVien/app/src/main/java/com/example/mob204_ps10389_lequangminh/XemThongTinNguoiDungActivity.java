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

public class XemThongTinNguoiDungActivity extends AppCompatActivity {
    TextView tvUserName, tvPhoneNumber, tvPassWord, tvFullName;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_thong_tin_nguoi_dung);
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

        tvUserName.setText(getIntent().getStringExtra("USERNAME"));
        tvPassWord.setText(getIntent().getStringExtra("PASSWORD"));
        tvFullName.setText(getIntent().getStringExtra("FULLNAME"));
        tvPhoneNumber.setText(getIntent().getStringExtra("PHONE"));

    }
    public void anhxa(){
        tvUserName = findViewById(R.id.tvUserName);
        tvFullName = findViewById(R.id.tvFullName);
        tvPassWord = findViewById(R.id.tvPassWord);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
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
