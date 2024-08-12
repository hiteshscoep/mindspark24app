package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;


public class WebView extends AppCompatActivity {
    public View progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.webview_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.pbar);
        android.webkit.WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
                setTitle("Loading...");
            }
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                setTitle(view.getTitle());
            }
        });
        String site = getIntent().getStringExtra("site");
        webView.loadUrl(site);

        //enabling js and some other stuff for interactivity to work
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);

        //To enable downloading from the site
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            DownloadManager.Request myRequest = new DownloadManager.Request(Uri.parse(url));
            myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            DownloadManager myManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            myManager.enqueue(myRequest);
            Toast.makeText(getApplicationContext(), "Downloading File...",Toast.LENGTH_LONG).show();
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}