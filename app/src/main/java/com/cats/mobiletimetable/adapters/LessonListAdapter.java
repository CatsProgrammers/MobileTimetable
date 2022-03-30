package com.cats.mobiletimetable.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cats.mobiletimetable.R;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;

import java.util.List;


public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.MyViewHolder> {

    public List<LessonWithDetails> lessonList;
    private Context context;


    public LessonListAdapter(Context context) {
        this.context = context;

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

        LessonWithDetails currentLesson = lessonList.get(position);
        holder.title.setText(currentLesson.lesson.name);
        holder.place.setText(currentLesson.lesson.auditorium);
        holder.teacher.setText(currentLesson.teacher.name);
        holder.timeBegin.setText(currentLesson.lesson.beginLesson);
        holder.timeEnd.setText(currentLesson.lesson.endLesson);

        //TODO: выставить lessonType в ImageView


    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView place;
        TextView teacher;
        TextView timeBegin;
        TextView timeEnd;
        ImageView lessonType;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.titleTextView);
            place = view.findViewById(R.id.placeTextView);
            teacher = view.findViewById(R.id.teacherTextView);
            timeBegin = view.findViewById(R.id.timeBeginTextView);
            timeEnd = view.findViewById(R.id.timeEndTextView);
            lessonType = view.findViewById(R.id.lessonTypeImageView);

        }
    }
}
