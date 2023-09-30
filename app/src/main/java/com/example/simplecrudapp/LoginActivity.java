package com.example.simplecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{

    private TextInputEditText emailEdit, passEdit;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize variables
        emailEdit = findViewById(R.id.textEditEmail);
        passEdit = findViewById(R.id.textEditPass);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

    }

    public void onLoginBtn(View view){

        // hide progress bar
        progressBar.setVisibility(View.VISIBLE);

        // get data from text edit
        String email = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();

        // validate, move to controller
        if(loginController.isEmpty(email, pass)){

            Toast.makeText(LoginActivity.this, "Make sure the fields are not empty", Toast.LENGTH_SHORT).show();
            return;

        }
        // call sign in method. validation done by firebase, no need more validation
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){

                // if login success
                if(task.isSuccessful()){

                    // hide progress bar
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    moveToMain();
                    finish();

                }
                else{

                    // Login failed
                    Exception exception = task.getException();
                    if (exception != null) {
                        Log.e("FirebaseAuth", "Login failed: " + exception.getMessage());
                    }

                    // display failure toast message
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login Failed, Please Try Again", Toast.LENGTH_SHORT).show();


                }

            }
        });

    }

    @Override
    protected void onStart(){

        super.onStart();

        // on start, check if user is already signed id
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){

//            moveToMain();

        }

    }

    public void onMoveToRegis(View view ){

        moveToRegis();

    }

    public void moveToRegis(){

        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);

    }

    public void moveToMain(){

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);

    }


}