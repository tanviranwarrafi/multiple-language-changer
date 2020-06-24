package com.example.multiplelanguagechanger;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button changelanguage;
    private Button changeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocate();
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        changelanguage = findViewById(R.id.change_language_button);
        changeActivity = findViewById(R.id.test);

        changeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivityIntent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(secondActivityIntent);
                finish();
                overridePendingTransition(R.anim.slide_up,  R.anim.no_animation);
            }
        });

        changelanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoeChangedlanguageDialog();
            }
        });
    }

    private void shoeChangedlanguageDialog() {

        final String[] listItems = {"French", "हिंदी", "বাংলা", "English"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
        mbuilder.setTitle("Change Language....");
        mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    setLocale("fr");
                    recreate();
                }
                else if (i == 1){
                    setLocale("hi");
                    recreate();
                }
                else if (i == 2){
                    setLocale("bn");
                    recreate();
                }
                if (i == 3){
                    // Default English
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mdialog = mbuilder.create();
        mdialog.show();
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

    private void loadLocate(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang", "");
        setLocale(language);
    }

}
