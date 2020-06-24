package com.example.multiplelanguagechanger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "SecondActivity";

    private ToggleButton changeLanguageSecondActivity;
    private Button goToFirstActivity;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocate();
        setContentView(R.layout.activity_second);

        goToFirstActivity = findViewById(R.id.goToFirstActivity);
        goToFirstActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
                finish();
            }
        });

        changeLanguageSecondActivity = (ToggleButton) findViewById(R.id.change_language_button_second_activity);
        changeLanguageSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: language: "+language);
                if (language.equals("en")) {
                    setLocale("bn");
                    language = "bn";
                    recreate();
                } else {
                    setLocale("en");
                    language = "en";
                    recreate();
                }
            }
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // Save Data to Shared Preference
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocate() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        language = sharedPreferences.getString("My_Lang", "");
        setLocale(language);
    }

}
