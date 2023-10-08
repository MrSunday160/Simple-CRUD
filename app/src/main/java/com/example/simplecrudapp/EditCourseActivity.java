package com.example.simplecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.TotpMultiFactorAssertion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditCourseActivity extends AppCompatActivity{

    // create variables
    private TextInputEditText courseNameEdit, coursePriceEdit, courseSuitedEdit, courseImgEdit, courseDescEdit;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CourseRVModal courseRVModal;
    private ProgressBar progressBar;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // initialize variables
        courseNameEdit = findViewById(R.id.textEditCourseName);
        coursePriceEdit = findViewById(R.id.textEditCoursePrice);
        courseSuitedEdit = findViewById(R.id.textEditCourseSuitedFor);
        courseImgEdit = findViewById(R.id.textEditCourseImageLink);
        courseDescEdit = findViewById(R.id.textEditCourseDesc);

        Button updateCourse = findViewById(R.id.updateCourseBtn);
        Button deleteCourse = findViewById(R.id.deleteCourseBtn);

        progressBar = findViewById(R.id.progressBar);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // get modal class
        courseRVModal = getIntent().getParcelableExtra("course");
        if(courseRVModal != null){

            // set data to edit text from modal class
            courseID = courseRVModal.getCourseId();
            courseNameEdit.setText(courseRVModal.getCourseName());
            coursePriceEdit.setText(courseRVModal.getCoursePrice());
            courseSuitedEdit.setText(courseRVModal.getBestSuitedFor());
            courseImgEdit.setText(courseRVModal.getCourseImg());
            courseDescEdit.setText(courseRVModal.getCourseDescription());

        }

        // initialize db reference, add child as courseID
        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);

    }

    public void onUpdateBtn(View view){

        // make progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        // get data from text edit
        String courseName = courseNameEdit.getText().toString();
        String courseDesc = courseDescEdit.getText().toString();
        String coursePrice = coursePriceEdit.getText().toString();
        String courseSuited = courseSuitedEdit.getText().toString();
        String courseImg = courseImgEdit.getText().toString();

        // create new map to pass data using key and value
        Map<String, Object> map = new HashMap<>();
        map.put("courseName", courseName);
        map.put("courseDescription", courseDesc);
        map.put("coursePrice", coursePrice);
        map.put("courseSuited", courseSuited);
        map.put("courseImg", courseImg);
        map.put("courseID", courseID);

        // call db reference on data change method
        databaseReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

                // progress bar gone
                progressBar.setVisibility(View.GONE);
                // add map to our db
                databaseReference.updateChildren(map);
                // display toast message
                Toast.makeText(EditCourseActivity.this, "Course Updated", Toast.LENGTH_SHORT).show();
                // open new activity
                moveToMain();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

                // failed message
                Toast.makeText(EditCourseActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void onDeleteBtn(View view){

        // call method to delete course from db
        databaseReference.removeValue();
        Toast.makeText(this, "Course Deleted", Toast.LENGTH_SHORT).show();
        moveToMain();

    }

    private void moveToMain(){

        Intent i = new Intent(EditCourseActivity.this, MainActivity.class);
        startActivity(i);

    }
}