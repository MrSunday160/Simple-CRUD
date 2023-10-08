package com.example.simplecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity{

    // create variables
    private TextInputEditText courseNameEdit, courseDescrEdit, coursePriceEdit,
        courseSuitedEdit, courseImgEdit;
    private ProgressBar progressBar;
    private String courseID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // initialize variables
        courseNameEdit = findViewById(R.id.textEditCourseName);
        courseDescrEdit = findViewById(R.id.textEditCourseDesc);
        coursePriceEdit = findViewById(R.id.textEditCoursePrice);
        courseSuitedEdit = findViewById(R.id.textEditCourseSuitedFor);
        courseImgEdit = findViewById(R.id.textEditCourseImageLink);

        progressBar = findViewById(R.id.progressBar);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // create our database reference
        databaseReference = firebaseDatabase.getReference("Courses" );

    }

    public void onCourseAddBtn(View view){

        // set progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        // get datas
        String courseName = courseNameEdit.getText().toString();
        String courseDesc = courseDescrEdit.getText().toString();
        String coursePrice = coursePriceEdit.getText().toString();
        String courseSuited = courseSuitedEdit.getText().toString();
        String courseImg = courseImgEdit.getText().toString();
        courseID = courseName;



        // pass data to modal class
        CourseRVModal courseRVModal = new CourseRVModal(courseID, courseName, courseDesc, coursePrice, courseSuited, courseImg);

        // call add value event to pass data into firebase db
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

                // set data in firebase db
                databaseReference.child(courseID).setValue(courseRVModal);
                // display toast message
                Toast.makeText(AddCourseActivity.this, "Course added", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(AddCourseActivity.this, MainActivity.class));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){

                // display error message
                Log.e("Firebase Error", "Error: " + error.getMessage());
                progressBar.setVisibility(View.GONE); // hide progress bar
                Toast.makeText(AddCourseActivity.this, "Failed to add course", Toast.LENGTH_SHORT).show();

            }
        });

    }
}