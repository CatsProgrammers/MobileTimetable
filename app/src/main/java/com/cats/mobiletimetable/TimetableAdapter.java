package com.cats.mobiletimetable;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.MyViewHolder> {

    String data1[];
    String data2[];
    Context context;


    public TimetableAdapter(Context ct, String s1[], String s2[]){
        data1 = s1;
        data2 = s2;
        context = ct;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view_item, parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.timetable_title.setText(data1[position]);
        holder.timetable_place.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView timetable_title, timetable_place;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            timetable_title = itemView.findViewById(R.id.email_title);
            timetable_place = itemView.findViewById(R.id.sender_title);

        }
    }
}
