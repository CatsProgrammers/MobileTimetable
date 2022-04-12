package com.cats.mobiletimetable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.R;
import com.cats.mobiletimetable.db.AppDatabase;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;

import java.util.List;


public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.MyViewHolder> {

    public List<LessonWithDetails> lessonList;
    private Context context;
    private AppDatabase db;

    public LessonListAdapter(Context context) {
        this.context = context;
        db = AppDatabase.getDbInstance(context);
    }

    public void setLessonList(List<LessonWithDetails> lessonList) {
        this.lessonList = lessonList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //TODO: Если дата у предыдущего урока не совпадает с текущей - показываем хедер
        if ((position == 0) || (false)) {
            holder.headerLayout.setVisibility(View.VISIBLE);
        } else {
            holder.headerLayout.setVisibility(View.GONE);
        }

        LessonWithDetails currentLesson = lessonList.get(position);

        holder.title.setText(currentLesson.lesson.name);
        holder.auditorium.setText(currentLesson.lesson.auditorium);
        holder.place.setText(currentLesson.building.label);
        holder.teacher.setText(currentLesson.teacher.name);
        holder.timeBegin.setText(currentLesson.lesson.beginLesson);
        holder.timeEnd.setText(currentLesson.lesson.endLesson);
        holder.group.setText(currentLesson.lesson.stream);


        switch (currentLesson.kindOfWork.name) {
            case ("Семинар"):
                holder.lessonTypeImage.setImageResource(R.drawable.circle_seminar);
                break;
            case ("Лекция"):
                holder.lessonTypeImage.setImageResource(R.drawable.circle_lecture);
                break;
            default:
                holder.lessonTypeImage.setImageResource(R.drawable.circle_other);
                break;
        }

        holder.lessonTypeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, currentLesson.kindOfWork.name, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout headerLayout;
        TextView title;
        TextView auditorium;
        TextView group;
        TextView teacher;
        TextView place;
        TextView timeBegin;
        TextView timeEnd;
        ImageView lessonTypeImage;

        public MyViewHolder(View view) {
            super(view);

            headerLayout = view.findViewById(R.id.headerLayout);
            title = view.findViewById(R.id.titleTextView);
            auditorium = view.findViewById(R.id.auditoriumTextView);
            group = view.findViewById(R.id.groupTextView);
            teacher = view.findViewById(R.id.teacherTextView);
            place = view.findViewById(R.id.placeTextView);
            timeBegin = view.findViewById(R.id.timeBeginTextView);
            timeEnd = view.findViewById(R.id.timeEndTextView);
            lessonTypeImage = view.findViewById(R.id.lessonTypeImageView);

        }
    }
}
