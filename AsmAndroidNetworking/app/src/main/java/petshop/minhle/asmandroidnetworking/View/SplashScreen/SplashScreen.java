package petshop.minhle.asmandroidnetworking.View.SplashScreen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.minhle.asmandroidnetworking.R;

import petshop.minhle.asmandroidnetworking.View.Home.HomeActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowHomeEnabled(false);
            //actionBar.setDisplayShowTitleEnabled(true);
            //actionBar.setDisplayUseLogoEnabled(false);
            actionBar.hide();
        }



        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                Thread.sleep(2000);
                } catch (Exception e) {

                } finally {
                    Intent ihome = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(ihome);
                    finish();
                }
            }
        });
        thread.start();

    }
}