package com.example.nftartist.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

import com.example.nftartist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_up_intent).setOnClickListener(onClickListener);
        findViewById(R.id.loginbtn).setOnClickListener(onClickListener);
        findViewById(R.id.resetInent).setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance();


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_up_intent:
                    signUpPageIntent();
                    break;
                case R.id.loginbtn:
                    login();
                    break;
                case R.id.resetInent:
                    resetPageIntent();
                    break;
            }
        }

    };

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void login(){
        String email = ((EditText)findViewById(R.id.email_login)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.password_login)).getText().toString().trim();

        if(email.length() > 0 &&email.contains("@") ){
            if(password.length()>5) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startToast("로그인에 성공했습니다.");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    mainPageIntent();
                                    finish();
                                } else {
                                    if (task.getException() != null) {
                                        startToast("이메일과 비밀번호를 확인해주세요");
                                    }
                                }
                            }
                        });
            }else {
                startToast("비밀번호를 입력해주세요.");
            }
        }else{
            startToast("이메일 형식이 올바르지않습니다.");
        }


    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void signUpPageIntent(){
        Intent intent = new Intent(Login.this, SignUp.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void mainPageIntent(){
        Intent intent = new Intent(Login.this,MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void resetPageIntent(){
        Intent intent = new Intent(Login.this,PasswordReset.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}