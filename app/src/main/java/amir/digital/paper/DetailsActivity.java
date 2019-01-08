package amir.digital.paper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import amir.digital.paper.Mnanger.StaticDataManager;
import amir.digital.paper.webView.MyAppWebViewClient;

public class DetailsActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private String url,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        url = intent.getStringExtra(StaticDataManager.url_key);
        title = intent.getStringExtra(StaticDataManager.title_key);
        setupActionbar();
        webView = findViewById(R.id.webView);

        webView.setWebViewClient(new MyAppWebViewClient(url));


        webView.getSettings().setJavaScriptEnabled(true);
        progressBar =findViewById(R.id.progressBar);

        webView.getSettings().setAllowFileAccess(true);


        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
    }

    private void setupActionbar() {
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}


