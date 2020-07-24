package petshop.minhle.asmandroidnetworking.View.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.minhle.asmandroidnetworking.R;

import petshop.minhle.asmandroidnetworking.Adapter.ViewPagerAdapter;
import petshop.minhle.asmandroidnetworking.View.Login.LoginActivity;

public class HomeActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.appbar);

        //xu ly toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //xu ly tabs
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //appbar
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.it_Login:
                Intent iLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(iLogin);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(collapsingToolbarLayout.getHeight() + verticalOffset <=  1.5 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)){
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.lnSearch);
            linearLayout.animate().alpha(0).setDuration(200);

//            MenuItem itSearch = menu.findItem(R.id.itSearch);
//            itSearch.setVisible(true);

        }else{
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.lnSearch);
            linearLayout.animate().alpha(1).setDuration(200);
//            try{
//                MenuItem itSearch = menu.findItem(R.id.itSearch);
//                itSearch.setVisible(false);
//            }catch (Exception e){
//
//            }

        }
    }
}