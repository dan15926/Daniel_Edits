package edu.upenn.cis350.projectcopy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterCourses extends RecyclerView.Adapter<RecyclerAdapterCourses.MyViewHolder> {
    private Course[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;
        public MyViewHolder(View v) {
            super(v);
            courseName = v.findViewById(R.id.course_name);
        }
    }

    public RecyclerAdapterCourses(Course[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.courseName.setText(mDataset[position].getCourseName());
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

