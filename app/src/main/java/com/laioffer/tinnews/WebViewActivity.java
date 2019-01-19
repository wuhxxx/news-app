package com.laioffer.tinnews;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.laioffer.tinnews.common.TinBasicActivity;
import com.laioffer.tinnews.common.TinBasicFragment;

public class WebViewActivity extends TinBasicActivity implements PopupMenu.OnMenuItemClickListener{
    private String url;
    private ImageView back;
    private ProgressBar progressBar;
    public static final String URL = "URL";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        progressBar = findViewById(R.id.progress_bar);
        final WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Bundle innerBundle = bundle.getBundle(BUNDLE);
            if (innerBundle != null) {
                url = innerBundle.getString(URL);
                webView.loadUrl(url);
            }
        }

        findViewById(R.id.more).setOnClickListener(v -> showMenu(v));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web_view;
    }


    @SuppressLint("RestrictedApi")
    private void showMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.setOnMenuItemClickListener(this);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.web_view_item, menu.getMenu());
        MenuPopupHelper menuHelper = new MenuPopupHelper(this, (MenuBuilder) menu.getMenu(), view);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                // pop out android's share view
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "From TinNews: \n" + url;
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                this.startActivity(Intent.createChooser(sharingIntent, "Share TinNews"));
                break;
            case R.id.menu_copy:
                // copy link to click board
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", url);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Link Copied", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return true;

    }


    @Override
    public void doFragmentTransaction(TinBasicFragment basicFragment) {

    }

    @Override
    public void showSnackBar(String message) {

    }
}
