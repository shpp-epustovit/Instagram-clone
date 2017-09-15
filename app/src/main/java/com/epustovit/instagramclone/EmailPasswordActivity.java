package com.epustovit.instagramclone;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    Boolean signUpModeActive = true;

    private TextView changeSignupModeTextView;
    private EditText userEmailEditText;
    private EditText passwordEditText;

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignupModeTextView) {

            Button signupButton = (Button) findViewById(R.id.signupButton);

            if (signUpModeActive) {

                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");

            } else {

                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Or, Login");

            }

        }

    }

    public void signUp(View view) {

        EditText userEmailEditText = (EditText) findViewById(R.id.userEmailEditText);

        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        if (userEmailEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

        } else {

            if (signUpModeActive) {

                registration(userEmailEditText.getText().toString(),passwordEditText.getText().toString());

            } else {

                signIn(userEmailEditText.getText().toString(),passwordEditText.getText().toString());

            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out

                }

            }
        };

        changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);

        changeSignupModeTextView.setOnClickListener(this);




    }


    public void signIn(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(EmailPasswordActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(EmailPasswordActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(EmailPasswordActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(EmailPasswordActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
