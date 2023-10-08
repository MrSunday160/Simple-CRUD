package com.example.simplecrudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder>{

    // variables for list, context, interface, and position
    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;
    int lastPos = -1;

    // constructor
    public CourseRVAdapter(ArrayList<CourseRVModal> courseRVModalArrayList, Context context, CourseClickInterface courseClickInterface){

        this.courseRVModalArrayList = courseRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;

    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        // inflating layout file
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position){

        // set data to recycler view
        CourseRVModal courseRVModal = courseRVModalArrayList.get(position);
        holder.courseNameTemp.setText(courseRVModal.getCourseName());
        holder.coursePriceTemp.setText("Rp. " + courseRVModal.getCoursePrice());
        Picasso.get().load(courseRVModal.getCourseImg()).into(holder.courseImgTemp);

        // add animation to recycler view item
        setAnimation(holder.itemView, position);
        holder.courseImgTemp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                courseClickInterface.onCourseClick(position);
            }
        });

    }

    private void setAnimation(View itemView, int position){

        if(position > lastPos){

            // set animation
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;

        }

    }

    @Override
    public int getItemCount(){
        return courseRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView courseImgTemp;
        private TextView courseNameTemp, coursePriceTemp;

        public ViewHolder(@NonNull View view){

            super(view);
            // initialize all variables
            courseImgTemp = view.findViewById(R.id.viewImage);
            courseNameTemp = view.findViewById(R.id.courseName);
            coursePriceTemp = view.findViewById(R.id.coursePrice);

        }

    }

    // interface for on click
    public interface CourseClickInterface{

        void onCourseClick(int position);

    }

}
