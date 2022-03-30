package com.cats.mobiletimetable;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    ArrayAdapter<String> groupListAdapter;
    AutoCompleteTextView groupTextView;

    ArrayAdapter<String> userTypeListAdapter;
    AutoCompleteTextView userTypeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        groupListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        groupTextView = findViewById(R.id.groupsList);
        groupTextView.setAdapter(groupListAdapter);

        //TODO: Тут юзануть простой Spinner, а не AutoCompleteTextView!!
        userTypeListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, USERTYPES);
        userTypeTextView = findViewById(R.id.userTypesList);
        userTypeTextView.setAdapter(userTypeListAdapter);
    }

    private static final String[] COUNTRIES = new String[] {"Belgium", "France", "Italy", "Germany", "Spain"
    };

    private static final String[] USERTYPES = new String[] {"Кочевник", "Дитя улиц", "Корпорат"};
}