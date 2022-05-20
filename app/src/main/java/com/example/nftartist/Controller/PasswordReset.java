package com.example.nftartist.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nftartist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordReset extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        findViewById(R.id.sent_Bnt).setOnClickListener(onClickListener);
        findViewById(R.id.login_back).setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sent_Bnt:
                    send();
                    break;
                case R.id.login_back:
                    loginPageIntent();

                    break;
            }
        }

    };


    private void send(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString().trim();

        if(email.length() > 0 &&email.contains("@") ){

            mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startToast("이메일을 보냈습니다.");
                                    loginPageIntent();
                                }
                            }
                        });
        }else{
            startToast("이메일을 입력해주세요.");
        }


    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void loginPageIntent(){
        Intent intent = new Intent(PasswordReset.this,Login.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}