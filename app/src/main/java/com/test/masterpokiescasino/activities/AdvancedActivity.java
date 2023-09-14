package com.test.masterpokiescasino.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.test.masterpokiescasino.R;
import com.test.masterpokiescasino.databinding.ActivityAdvancedBinding;
import com.test.masterpokiescasino.databinding.ActivityMainBinding;
import com.test.masterpokiescasino.fragments.MainFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import im.delight.android.webview.AdvancedWebView;

public class AdvancedActivity extends AppCompatActivity implements AdvancedWebView.Listener{

    ActivityAdvancedBinding binding;
    private AdvancedWebView webView;
    private int code = 111;
    private ValueCallback<Uri[]> callback;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvancedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webView = binding.advancedView;
        webView.setListener(this, this);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setBackgroundColor(Color.WHITE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        CookieManager cookieManager = CookieManager.getInstance();
        CookieManager.setAcceptFileSchemeCookies(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);
        setWebChromeClient();
        webView.loadUrl(getIntent().getExtras().getString("url"));
    }

    private void setWebChromeClient()
    {
        webView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            private void SetDexter() {
                Dexter.withContext(AdvancedActivity.this)
                        .withPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            }
                        }).check();
            }

            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                SetDexter();

                callback = filePathCallback;
                Intent intentOne = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File filetoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = null;
                try {
                    file = File.createTempFile("pokies" +
                            new SimpleDateFormat("yyyyMMdd_HHmmss",
                                    Locale.getDefault()).
                                    format(new Date()) + "_", ".jpg", filetoDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (file != null) {
                    Uri fromFile = FileProvider(file);
                    uri = fromFile;
                    intentOne.putExtra(MediaStore.EXTRA_OUTPUT, fromFile);
                    intentOne.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    Intent intentTwo = new Intent(Intent.ACTION_GET_CONTENT);
                    intentTwo.addCategory(Intent.CATEGORY_OPENABLE);
                    intentTwo.setType("image/*");

                    Intent intentChooser = new Intent(Intent.ACTION_CHOOSER);
                    intentChooser.putExtra(Intent.EXTRA_INTENT, intentOne);
                    intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intentTwo});

                    startActivityForResult(intentChooser, code);

                    return true;
                }
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }

            Uri FileProvider(File file) {
                return FileProvider.getUriForFile(AdvancedActivity.this, getApplication().getPackageName() + ".provider", file);
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        binding.advancedView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        binding.advancedView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        binding.advancedView.onDestroy();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        binding.advancedView.onActivityResult(requestCode, resultCode, intent);
        if (code == requestCode)
            if (callback == null) return;
        if (resultCode != -1) {
            callback.onReceiveValue(null);
            return;
        }
        Uri result = (intent == null)? uri : intent.getData();
        if(result != null && callback != null)
            callback.onReceiveValue(new Uri[]{result});
        else if(callback != null)
            callback.onReceiveValue(new Uri[] {uri});
        callback = null;
    }

    @Override
    public void onBackPressed() {
        if (!binding.advancedView.onBackPressed()) { return; }

        super.onBackPressed();
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }
}