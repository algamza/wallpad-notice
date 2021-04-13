package com.wallpad.parking.view.common;

import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wallpad.parking.R;

public class BaseActivity extends AppCompatActivity {
    private final String theme_key = "com.wallpad.settings.theme";
    private int currentTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTheme = Settings.Global.getInt(getContentResolver(), theme_key, 0);
        setAppTheme(currentTheme);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int theme = Settings.Global.getInt(getContentResolver(), theme_key, 0);
        if ( currentTheme != theme ) recreate();
    }

    private void setAppTheme(int theme) {
        switch (theme) {
            case 0: setTheme(R.style.KDWallPad_Dark_CotaCharcoal); break;
            case 1: setTheme(R.style.KDWallPad_Dark_GlamNavy); break;
            case 2: setTheme(R.style.KDWallPad_Light_SatinBeige); break;
            case 3: setTheme(R.style.KDWallPad_Light_SatinGray); break;
        }
    }
}