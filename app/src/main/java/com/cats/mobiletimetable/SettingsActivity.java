package com.cats.mobiletimetable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cats.mobiletimetable.api.AppApi;
import com.cats.mobiletimetable.api.RuzApi;
import com.cats.mobiletimetable.converters.DbConverter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.tables.Setting;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    List<String> userTypes;
    AutoCompleteTextView autoCompleteTextView;

    Spinner spinner;
    AppDatabase db;
    RuzApi api;

    DbConverter dbConverter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        spinner = findViewById(R.id.userTypeSpinner);

        db = AppDatabase.getDbInstance(getApplicationContext());
        dbConverter = new DbConverter(db);
        api = AppApi.getRuzApiInstance(getApplicationContext());

        userTypes = Arrays.asList(getResources().getStringArray(R.array.user_types));
        spinnerInit();

        spinnerBaseInit();

    }

    private void spinnerBaseInit() {
        //Если студент
        if (spinner.getSelectedItem().equals(userTypes.get(0))) {
            studentTypeInit();
        } else if (spinner.getSelectedItem().equals(userTypes.get(1))) {
            teacherTypeInit();
        }
        //TODO: Аудитория (?)
    }

    private void spinnerInit() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, userTypes);
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

        Log.i("autoCompleteTextView", "отрабатывает teacherTypeInit");
        List<String> teachersList = dbConverter.teacherToStringConverter(db.teacherDao().getAllTeachers());

        String teacherSettingsKey = Utils.teacherSettingsKey;

        Setting item = db.settingsDao().getItemByName(teacherSettingsKey);

        if (item != null) {
            autoCompleteTextView.setText(item.value);
        }

        ArrayAdapter<String> teacherListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, teachersList);
        autoCompleteTextView.setAdapter(teacherListAdapter);

        //Когда нажимаем на наш AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {

                String currentTeacherString = autoCompleteTextView.getText().toString();

                //Если есть уже какое-то значение в БД, то удаляем его
                if (db.settingsDao().getItemByName(teacherSettingsKey) != null) {
                    db.settingsDao().deleteItem(teacherSettingsKey);
                }

                Setting item = new Setting();
                item.name = teacherSettingsKey;
                item.value = currentTeacherString;
                db.settingsDao().insertItem(item);

                Toast.makeText(getApplicationContext(), "Преподаватель " + currentTeacherString + " выбран", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void studentTypeInit() {

        Log.i("autoCompleteTextView", "отрабатывает studentTypeInit");

        String groupSettingsKey = Utils.groupSettingsKey;

        Setting item = db.settingsDao().getItemByName(groupSettingsKey);

        if (item != null) {
            autoCompleteTextView.setText(item.value);
        }

        List<String> groupsList = dbConverter.groupToStringConverter(db.groupDao().getAllGroups());
        ArrayAdapter<String> groupListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, groupsList);
        autoCompleteTextView.setAdapter(groupListAdapter);
        //Когда нажимаем на наш AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {

                String currentGroupString = autoCompleteTextView.getText().toString();

                //Если есть уже какое-то значение в БД, то удаляем его
                if (db.settingsDao().getItemByName(groupSettingsKey) != null) {
                    db.settingsDao().deleteItem(groupSettingsKey);
                }

                Setting item = new Setting();
                item.name = groupSettingsKey;
                item.value = currentGroupString;
                db.settingsDao().insertItem(item);

                Toast.makeText(getApplicationContext(), "Группа " + currentGroupString + " выбрана", Toast.LENGTH_SHORT).show();

            }
        });

    }
}