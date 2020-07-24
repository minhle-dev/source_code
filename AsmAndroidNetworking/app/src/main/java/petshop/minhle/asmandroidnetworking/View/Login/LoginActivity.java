package petshop.minhle.asmandroidnetworking.View.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.minhle.asmandroidnetworking.R;

import petshop.minhle.asmandroidnetworking.Adapter.ViewPagerAdapterLogin;

public class LoginActivity extends AppCompatActivity {
    TabLayout LoginTabLayout;
    ViewPager LoginViewPager;
    Toolbar LoginToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginTabLayout = findViewById(R.id.tabLogin);
        LoginViewPager = findViewById(R.id.viewpagerLogin);
        LoginToolbar = findViewById(R.id.toolbarLogin);
        //xuly toolbar
        LoginToolbar.setTitle("Đăng nhập/ Đăng ký");
        setSupportActionBar(LoginToolbar);
        //xu ly tabs
        ViewPagerAdapterLogin viewPagerAdapterLogin = new ViewPagerAdapterLogin(getSupportFragmentManager());
        LoginViewPager.setAdapter(viewPagerAdapterLogin);
        viewPagerAdapterLogin.notifyDataSetChanged();

        LoginTabLayout.setupWithViewPager(LoginViewPager);
    }
}