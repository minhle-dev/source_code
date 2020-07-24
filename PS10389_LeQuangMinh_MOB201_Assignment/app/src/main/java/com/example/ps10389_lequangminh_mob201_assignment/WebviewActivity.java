package com.example.ps10389_lequangminh_mob201_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webView);
        Bundle bundle = getIntent().getExtras();
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(bundle.getString("Link"));
    }
}
