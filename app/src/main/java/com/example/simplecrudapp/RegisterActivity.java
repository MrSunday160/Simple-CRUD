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

public class RegisterActivity extends AppCompatActivity{

    private TextInputEditText emailEdit, passEdit, confirmPass;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize all variables
        emailEdit = findViewById(R.id.textEditEmail);
        passEdit = findViewById(R.id.textEditPass);
        confirmPass = findViewById(R.id.textEditConfPass);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

    }


    public void onRegisBtn(View view){

        // hide progress bar
        progressBar.setVisibility(View.VISIBLE);

        // get data from text edit
        String email = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();
        String passConf = confirmPass.getText().toString();

        // validation, move to controller
        // if password does not match
        if(regisController.isPassNotMatch(pass, passConf))
            Toast.makeText(RegisterActivity.this, "Make sure the passwords match", Toast.LENGTH_SHORT).show();

        // if fields empty
        else if(regisController.isEmpty(email, pass, passConf))
            Toast.makeText(RegisterActivity.this, "Make sure the fields are not empty", Toast.LENGTH_SHORT).show();

        // validation complete, move to user creation
        else{

            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){

                            // check if task successful or not
                            if(task.isSuccessful()){

                                // we hide our progress bar, and open loginactivity
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                moveToLogin();
                                finish();

                            }

                            else{

                                // Registration failed
                                Exception exception = task.getException();
                                if (exception != null) {
                                    Log.e("FirebaseAuth", "Registration failed: " + exception.getMessage());
                                }

                                // display failure toast message
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "User Registration Failed", Toast.LENGTH_SHORT).show();


                            }

                        }
                    }
            );

        }


    }

    public void onMoveToLogin(View view){

        moveToLogin();

    }

    public void moveToLogin(){

        // move to login
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);

    }

}