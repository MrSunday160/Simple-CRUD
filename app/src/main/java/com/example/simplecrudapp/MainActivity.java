package com.example.simplecrudapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CourseRVAdapter.CourseClickInterface{

    // create variables
    private FloatingActionButton addCourseFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView courseRV;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private CourseRVAdapter courseRVAdapter;
    private RelativeLayout homeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize all variables
        courseRV = findViewById(R.id.RVcourses);
        homeRL = findViewById(R.id.RLHome);
        progressBar = findViewById(R.id.progressBar);
        addCourseFAB = findViewById(R.id.FABAdd);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        courseRVModalArrayList = new ArrayList<>();

        // get db references
        databaseReference = firebaseDatabase.getReference("Courses");

        // initialize adapter
        courseRVAdapter = new CourseRVAdapter(courseRVModalArrayList, this, this::onCourseClick);

        // set layout manager aligner to recyler view
        courseRV.setLayoutManager(new LinearLayoutManager(this));

        // call method to fetch courses from db
        getCourses();

    }

    private void getCourses(){

        // clear list
        courseRVModalArrayList.clear();

        databaseReference.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){
                // hide progress bar
                progressBar.setVisibility(View.GONE);

                // add snapshot to array list
                courseRVModalArrayList.add(snapshot.getValue(CourseRVModal.class));

                // notify adapter data has changed
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){

                //when new child is added
                // notify adapter and make progress bar hidden
                progressBar.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot){

                // notify when child removed
                progressBar.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){

                progressBar.setVisibility(View.GONE);
                courseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });

    }

    public void onFABClick(View view){

        // open new activity to add course
        Intent i = new Intent(MainActivity.this, AddCourseActivity.class);
        startActivity(i);

    }


    @Override
    public void onCourseClick(int position){

        // call display bottom sheet
        displayBottomSheet(courseRVModalArrayList.get(position));

    }

    public void displayBottomSheet(CourseRVModal modal){

        // create our bottom sheet dialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        // inflate layout file
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);

        // set content view for bottom sheet
        bottomSheetDialog.setContentView(layout);
        // set cancel-able
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        // call method to display bottom sheet
        bottomSheetDialog.show();

        // create variables for text view and image view inside the bottome sheet
        // initialize them with their ids
        TextView courseNameTv = layout.findViewById(R.id.courseNameTV);
        TextView courseDescTv = layout.findViewById(R.id.courseDescTV);
        TextView suitedTv = layout.findViewById(R.id.courseSuitedTV);
        TextView priceTv = layout.findViewById(R.id.coursePriceTV);
        ImageView imgTv = layout.findViewById(R.id.courseImgTV);

        Button viewBtn = layout.findViewById(R.id.buttonViewDetailTV);
        Button editBtn = layout.findViewById(R.id.buttonEditTV);

        // set data to different views
        courseNameTv.setText(modal.getCourseName());
        courseDescTv.setText(modal.getCourseDescription());
        suitedTv.setText(modal.getBestSuitedFor());
        priceTv.setText(modal.getCoursePrice());
        Picasso.get().load(modal.getCourseId()).into(imgTv);

        // add on click listener since we're still inside main activity class but different context
        viewBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                // navigate to browser for displaying course details
                // but since im not implementing this, we make it vibrate
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(200);

            }

        });

        editBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                // open edit course activity
                Intent i = new Intent(MainActivity.this, EditCourseActivity.class);
                i.putExtra("course", modal);
                startActivity(i);

            }

        });

    }

}