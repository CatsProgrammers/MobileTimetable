package com.cats.mobiletimetable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

public class TeacherInfoActivity extends AppCompatActivity {

    TextView teacherName;
    TextView teacherRank;
    TextView teacherEmail;
    Button readyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);

        teacherName = findViewById(R.id.teacherName);
        teacherRank = findViewById(R.id.teacherRank);
        teacherEmail = findViewById(R.id.teacherEmail);
        readyButton = findViewById(R.id.readyTeacherButton);

        View.OnClickListener readyButtonListener = this::readyButtonClicked;
        readyButton.setOnClickListener(readyButtonListener);

        String teacherNameDescription = "<b>Преподаватель:</b> " + getIntent().getStringExtra("name");
        String teacherRankDescription = "<b>Степень:</b> " + getIntent().getStringExtra("rank");
        String teacherEmailDescription = "<b>E-mail:</b> " + getIntent().getStringExtra("email");

        teacherName.setText(HtmlCompat.fromHtml(teacherNameDescription, HtmlCompat.FROM_HTML_MODE_LEGACY));
        teacherRank.setText(HtmlCompat.fromHtml(teacherRankDescription, HtmlCompat.FROM_HTML_MODE_LEGACY));
        teacherEmail.setText(HtmlCompat.fromHtml(teacherEmailDescription, HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    private void readyButtonClicked(View b) {
        finish();
    }
}