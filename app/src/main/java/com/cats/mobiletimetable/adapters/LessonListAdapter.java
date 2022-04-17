package com.cats.mobiletimetable.adapters;

import android.content.Context;
import android.graphics.Paint;
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
import com.cats.mobiletimetable.Utils;
import com.cats.mobiletimetable.db.relations.LessonWithDetails;

import java.util.List;


public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.MyViewHolder> {

    public List<LessonWithDetails> lessonList;
    private Context context;
    private LessonListener lessonListener;

    public LessonListAdapter(LessonListener lessonListener) {
        this.lessonListener = lessonListener;
    }

    public void setLessonList(List<LessonWithDetails> lessonList) {
        this.lessonList = lessonList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_view_item, parent, false);
        return new MyViewHolder(view, lessonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        LessonWithDetails currentLesson = lessonList.get(position);

        //Если дата у предыдущего урока не совпадает с текущей - показываем хедер
        if ((position == 0) || (!lessonList.get(position - 1).lesson.date.equals(currentLesson.lesson.date))) {
            holder.headerLayout.setVisibility(View.VISIBLE);
            holder.headerTextView.setText(Utils.headerDateFormatter(currentLesson.lesson.date));
        } else {
            holder.headerLayout.setVisibility(View.GONE);
        }

        holder.title.setText(currentLesson.lesson.name);
        holder.auditorium.setText(currentLesson.lesson.auditorium);
        holder.place.setText(currentLesson.building.label);
        holder.teacher.setText(currentLesson.teacher.name);
        holder.teacher.setPaintFlags(holder.teacher.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

        holder.lessonTypeImage.setOnClickListener(v -> Toast.makeText(context, currentLesson.kindOfWork.name, Toast.LENGTH_SHORT).show());

    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public interface LessonListener {
        void onTeacherLabelClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout headerLayout;
        TextView headerTextView;
        TextView title;
        TextView auditorium;
        TextView group;
        TextView teacher;
        TextView place;
        TextView timeBegin;
        TextView timeEnd;
        ImageView lessonTypeImage;
        LessonListener lessonListener;

        public MyViewHolder(View view, LessonListener lessonListener) {
            super(view);

            this.lessonListener = lessonListener;
            headerLayout = view.findViewById(R.id.headerLayout);
            headerTextView = view.findViewById(R.id.headerTextView);
            title = view.findViewById(R.id.titleTextView);
            auditorium = view.findViewById(R.id.auditoriumTextView);
            group = view.findViewById(R.id.groupTextView);
            teacher = view.findViewById(R.id.teacherTextView);
            place = view.findViewById(R.id.placeTextView);
            timeBegin = view.findViewById(R.id.timeBeginTextView);
            timeEnd = view.findViewById(R.id.timeEndTextView);
            lessonTypeImage = view.findViewById(R.id.lessonTypeImageView);

            View.OnClickListener teacherClickListener = this::teacherLabelClick;
            teacher.setOnClickListener(teacherClickListener);
        }

        public void teacherLabelClick(View v) {
            lessonListener.onTeacherLabelClick(getAdapterPosition());
        }
    }
}
