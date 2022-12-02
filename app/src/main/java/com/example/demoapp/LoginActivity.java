package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPass;
    Button Login;

    FirebaseAuth mAuth;
    public static String userNameText;
    public static String passwordText;

    boolean isCheckMember = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        Button login = findViewById(R.id.Login);
        Button signup = findViewById(R.id.signup);
        CheckBox isMemberChkBx = (CheckBox) findViewById(R.id.checkBoxLoginMember);
        isMemberChkBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("LoginActivityAlvin", "isMemberChkBx: "+isMemberChkBx.isChecked());
                isCheckMember = isMemberChkBx.isChecked();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity","Click Login Button");
                Intent login = new Intent(LoginActivity.this,MainPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("key1", "GFG :- Main Activity");

////        pass user input
                loginEmail = findViewById(R.id.loginEmail);
                loginPass = findViewById(R.id.loginPass);

                EditText username = (EditText) findViewById(R.id.loginEmail);
                EditText password = (EditText) findViewById(R.id.loginPass);
                //initiate a check box
                CheckBox memberCheckBox = (CheckBox) findViewById(R.id.checkBoxLoginMember);


                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                bundle.putString("username", usernameText);
                bundle.putString("password", passwordText);
                bundle.putBoolean("memberCheckBox",isCheckMember);

                Log.d("LoginActivity-Login", "isMemberChkBx: "+isCheckMember);

                Log.d("LoginActivity","username"+usernameText);
                Log.d("LoginActivity","password"+passwordText);
                Log.d("LoginActivity","memberCheckBox"+isCheckMember);

                login.putExtras(bundle);
                startActivity(login);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, RegistrationForm.class);

                Bundle bundle = new Bundle();
                bundle.putString("key1", "GFG :- Main Activity");

////        pass user input
                loginEmail = findViewById(R.id.loginEmail);
                loginPass = findViewById(R.id.loginPass);

                EditText username = (EditText) findViewById(R.id.loginEmail);
                EditText password = (EditText) findViewById(R.id.loginPass);
                //initiate a check box
                CheckBox memberCheckBox = (CheckBox) findViewById(R.id.checkBoxLoginMember);


                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                bundle.putString("username", usernameText);
                bundle.putString("password", passwordText);
                bundle.putBoolean("memberCheckBox",isCheckMember);

                Log.d("LoginActivity-SignUp", "isMemberChkBx: "+isCheckMember);

                signup.putExtras(bundle);

                startActivity(signup);
            }
        });

        loginEmail = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPass);
        Login = findViewById(R.id.Login);
        
        mAuth = FirebaseAuth.getInstance();

        Login.setOnClickListener(view -> {
            loginUser();
        });


    }

    private void loginUser() {
        setContentView(R.layout.activity_login2);
        String email = loginEmail.getText().toString();
        Log.d("LoginActivity-LoginUser", "isMemberChkBx: "+isCheckMember);
        String password = loginPass.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Email cannot be empty");
            loginPass.requestFocus();

        }else if (TextUtils.isEmpty(password)){
           loginPass.setError("Password cannot be empty");
           loginPass.requestFocus();


        }else {
            Log.d("LOGIN", "email:"+email);
            Log.d("LOGIN", "pass:"+password);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent myintent = new Intent(LoginActivity.this, MainPage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key1", "GFG :- Main Activity");

////        pass user input
                        loginEmail = findViewById(R.id.loginEmail);
                        loginPass = findViewById(R.id.loginPass);

                        Log.d("LoginActivity-LoginUser", "isMemberChkBx: "+isCheckMember);

                        bundle.putString("username", email);
                        bundle.putString("password", password);
                        bundle.putBoolean("memberCheckBox",isCheckMember);

                        myintent.putExtras(bundle);

                        startActivity(myintent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}