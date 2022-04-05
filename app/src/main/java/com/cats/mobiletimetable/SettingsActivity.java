package com.cats.mobiletimetable;

import android.os.Bundle;
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

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    ArrayAdapter<String> groupListAdapter;
    AutoCompleteTextView groupTextView;

    Spinner userTypeSpinner;
    AppDatabase db;
    RuzApi api;


    DbConverter dbConverter;
    List<String> groups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        groupTextView = findViewById(R.id.groupsList);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);

        db = AppDatabase.getDbInstance(getApplicationContext());
        dbConverter = new DbConverter(db);
        api = AppApi.getRuzApiInstance(getApplicationContext());

        Setting item = db.settingsDao().getItemByName("currentGroup");

        if (item != null) {
            groupTextView.setText(item.value);
        }

        groups = dbConverter.groupToStringConverter(db.groupDao().getAllGroups());
        groupListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, groups);
        groupTextView.setAdapter(groupListAdapter);

        //Когда нажимаем на наш AutoCompleteTextView
        groupTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {

                String currentGroupString = groupTextView.getText().toString();


                //TODO обновление значения, если оно уже есть в БД, а не еще один insert!
                Setting item = new Setting();
                item.name = "currentGroup";
                item.value = currentGroupString;
                db.settingsDao().insertItem(item);

                Toast.makeText(getApplicationContext(), "Группа " + currentGroupString + " выбрана", Toast.LENGTH_SHORT).show();

            }
        });
    }
}