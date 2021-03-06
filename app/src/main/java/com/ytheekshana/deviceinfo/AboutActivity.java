package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = sharedPrefs.getInt("ThemeBar", R.style.AppTheme);
        int themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        int themeColorDark = GetDetails.getDarkColor(this, themeColor);
        setTheme(themeId);

        if (sharedPrefs.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(themeColor));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getWindow().setStatusBarColor(themeColorDark);
        }
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
        setTaskDescription(taskDescription);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        context = this;

        TextView txtVersion = findViewById(R.id.txtVersion);
        TextView txtPackageName = findViewById(R.id.txtPackageName);
        txtPackageName.setText(getApplicationContext().getPackageName());
        txtVersion.setText(BuildConfig.VERSION_NAME);
        ImageView circleimgAuthor = findViewById(R.id.circleimgAuthor);
        Button btnDonate = findViewById(R.id.btnDonate);
        btnDonate.setOnClickListener(view -> {
            Intent intent = new Intent(context, DonateActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
        });

        circleimgAuthor.setOnClickListener(view -> {
            Snackbar snackAuthor = Snackbar.make(view, "Created by Ytheekshana", Snackbar.LENGTH_LONG);
            SnackbarHelper.configSnackbar(context, snackAuthor);
            snackAuthor.show();
        });
    }
}
