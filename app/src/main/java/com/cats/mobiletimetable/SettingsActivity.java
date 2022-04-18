package com.cats.mobiletimetable;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.cats.mobiletimetable.api.AppApi;
import com.cats.mobiletimetable.api.RuzApi;
import com.cats.mobiletimetable.converters.GroupConverter;
import com.cats.mobiletimetable.converters.TeacherConverter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.tables.Setting;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    List<String> userTypes;
    AutoCompleteTextView autoCompleteTextView;
    TextView criteriaTextView;
    Button readyButton;
    SwitchMaterial themeSwitch;

    Spinner spinner;
    AppDatabase db;
    RuzApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        spinner = findViewById(R.id.userTypeSpinner);
        criteriaTextView = findViewById(R.id.criteriaTextView);
        readyButton = findViewById(R.id.readySettingsButton);
        themeSwitch = findViewById(R.id.themeSwitch);

        View.OnClickListener readyButtonListener = this::readyButtonClicked;
        readyButton.setOnClickListener(readyButtonListener);

        db = AppDatabase.getDbInstance(getApplicationContext());
        api = AppApi.getRuzApiInstance(getApplicationContext());

        userTypes = Arrays.asList(getResources().getStringArray(R.array.user_types));

        switchInit();
        spinnerInit();
        spinnerBaseInit();

    }

    private void switchInit() {
        themeSwitch.setChecked((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES);
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                db.settingsDao().deleteItem(Utils.themeSettingsKey);
                Setting setting = new Setting();
                setting.name = Utils.themeSettingsKey;
                setting.value = "dark";
                db.settingsDao().insertItem(setting);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                db.settingsDao().deleteItem(Utils.themeSettingsKey);
                Setting setting = new Setting();
                setting.name = Utils.themeSettingsKey;
                setting.value = "light";
                db.settingsDao().insertItem(setting);
            }
        });
    }

    private void readyButtonClicked(View v) {
        finish();
    }

    private void spinnerBaseInit() {
        //Если студент
        if (spinner.getSelectedItem().equals(userTypes.get(0))) {
            studentTypeInit();
        } else if (spinner.getSelectedItem().equals(userTypes.get(1))) {
            teacherTypeInit();
        }
    }

    private void spinnerInit() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, userTypes);
        spinner.setAdapter(adapter);

        //Если был выбран какой-либо тип - отображаем его
        String userTypeSettingsKey = Utils.userTypeSettingsKey;
        Setting item = db.settingsDao().getItemByName(userTypeSettingsKey);
        if (item != null) {
            spinner.setSelection(userTypes.indexOf(item.value));
        }

        //Изменение спинера
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String currentValue = userTypes.get(position);

                //Если есть уже какое-то значение в БД, то удаляем его
                if (db.settingsDao().getItemByName(userTypeSettingsKey) != null) {
                    db.settingsDao().deleteItem(userTypeSettingsKey);
                }

                Setting item = new Setting();
                item.name = userTypeSettingsKey;
                item.value = currentValue;
                db.settingsDao().insertItem(item);

                spinnerBaseInit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    private void teacherTypeInit() {

        TeacherConverter teacherConverter = new TeacherConverter();

        Log.i("autoCompleteTextView", "отрабатывает teacherTypeInit");
        List<String> teachersList = teacherConverter.convertToString(db.teacherDao().getAllTeachers());

        String teacherSettingsKey = Utils.teacherSettingsKey;

        Setting item = db.settingsDao().getItemByName(teacherSettingsKey);

        criteriaTextView.setText("Выбранный преподаватель:");
        autoCompleteTextView.setText("");
        if (item != null) {
            autoCompleteTextView.setText(item.value);
        }

        ArrayAdapter<String> teacherListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, teachersList);
        autoCompleteTextView.setAdapter(teacherListAdapter);

        //Когда нажимаем на наш AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener((p, v, pos, id) -> {

            String currentTeacherString = autoCompleteTextView.getText().toString();

            //Если есть уже какое-то значение в БД, то удаляем его
            if (db.settingsDao().getItemByName(teacherSettingsKey) != null) {
                db.settingsDao().deleteItem(teacherSettingsKey);
            }

            Setting item1 = new Setting();
            item1.name = teacherSettingsKey;
            item1.value = currentTeacherString;
            db.settingsDao().insertItem(item1);

            Toast.makeText(getApplicationContext(), "Преподаватель " + currentTeacherString + " выбран", Toast.LENGTH_SHORT).show();

        });

    }

    private void studentTypeInit() {

        GroupConverter groupConverter = new GroupConverter();

        Log.i("autoCompleteTextView", "отрабатывает studentTypeInit");

        String groupSettingsKey = Utils.groupSettingsKey;

        Setting item = db.settingsDao().getItemByName(groupSettingsKey);

        criteriaTextView.setText("Выбранная группа:");
        autoCompleteTextView.setText("");
        if (item != null) {
            autoCompleteTextView.setText(item.value);
        }

        List<String> groupsList = groupConverter.convertToString(db.groupDao().getAllGroups());
        ArrayAdapter<String> groupListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, groupsList);
        autoCompleteTextView.setAdapter(groupListAdapter);
        //Когда нажимаем на наш AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener((p, v, pos, id) -> {

            String currentGroupString = autoCompleteTextView.getText().toString();

            //Если есть уже какое-то значение в БД, то удаляем его
            if (db.settingsDao().getItemByName(groupSettingsKey) != null) {
                db.settingsDao().deleteItem(groupSettingsKey);
            }

            Setting item1 = new Setting();
            item1.name = groupSettingsKey;
            item1.value = currentGroupString;
            db.settingsDao().insertItem(item1);

            Toast.makeText(getApplicationContext(), "Группа " + currentGroupString + " выбрана", Toast.LENGTH_SHORT).show();

        });

    }
}