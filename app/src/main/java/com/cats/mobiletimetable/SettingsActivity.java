package com.cats.mobiletimetable;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cats.mobiletimetable.api.AppApi;
import com.cats.mobiletimetable.api.GroupResponseModel;
import com.cats.mobiletimetable.api.TimetableAPI;
import com.cats.mobiletimetable.converters.DbConverter;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.tables.Group;
import com.cats.mobiletimetable.db.tables.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    ArrayAdapter<String> groupListAdapter;
    AutoCompleteTextView groupTextView;

    Spinner userTypeSpinner;
    AppDatabase db;
    TimetableAPI api;



    List<String> groups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        groups = new ArrayList<>();
        groupListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, groups);

        groupTextView = findViewById(R.id.groupsList);
        groupTextView.setAdapter(groupListAdapter);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);

        db = AppDatabase.getDbInstance(getApplicationContext());
        api = AppApi.getApiInstance(getApplicationContext());

        Setting item = db.settingsDao().getItemByName("currentGroup");

        if (item != null){
            groupTextView.setText(item.value);
        }

        groupTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Если меньше 5 символов - ниче не делаем
                if (s.toString().length() < 4){
                    return;
                }

                Call<List<GroupResponseModel>> call = api.getGroupByString(s.toString(), "group");
                call.enqueue(new Callback<List<GroupResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<GroupResponseModel>> call, Response<List<GroupResponseModel>> response) {
                        if ((response.isSuccessful()) && (response.body() != null)) {

                            //TODO: запись в БД тех групп, которых там нет

                            groupListAdapter.clear();
                            List<GroupResponseModel> currentGroupList = response.body();
                            for (GroupResponseModel group: currentGroupList) {
                                groupListAdapter.add(group.label);
                            }
                            groupListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GroupResponseModel>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });


        //Когда нажимаем на наш AutoCompleteTextView
        groupTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {

                String currentGroupString = groupTextView.getText().toString();

                Setting item = new Setting();
                item.name = "currentGroup";
                item.value = currentGroupString;
                db.settingsDao().insertItem(item);

                Toast.makeText(getApplicationContext(), "Группа "+currentGroupString+" выбрана", Toast.LENGTH_SHORT).show();

            }
        });
    }
}