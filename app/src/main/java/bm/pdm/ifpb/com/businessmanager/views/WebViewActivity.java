package bm.pdm.ifpb.com.businessmanager.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import bm.pdm.ifpb.com.businessmanager.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        this.webView = findViewById(R.id.web);
        this.settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        webView.loadUrl("https://business-manager-server.herokuapp.com/inicial");
    }
}
