package com.example.nftartist.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nftartist.Model.userInfo;
import com.example.nftartist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    private static final String TAG ="SignupActivity";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.sent_Bnt).setOnClickListener(onClickListener);
        findViewById(R.id.login_back).setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance();

    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sent_Bnt:
                     signUp();
                     break;
                case R.id.login_back:
                    loginPageIntent();
                    break;
            }
        }

    };

    private void profileUpdate(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String photoUrl = "0";
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString().trim();
        String nicName = ((EditText)findViewById(R.id.nicNameEditText)).getText().toString().trim();
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString().trim();

        if(user != null){
            userInfo Info = new userInfo(name,nicName,email,photoUrl);
            db.collection(user.getUid()).document("Profile").set(Info);

        }else{

        }
    }

    private void signUp(){
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString().trim();
        String nicName = ((EditText)findViewById(R.id.nicNameEditText)).getText().toString().trim();
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString().trim();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString().trim();

        if(name.length()>1 && nicName.length()>1){
            if(email.length() > 0 &&email.contains("@")){
                if(password.length() > 5 && passwordCheck.length() > 5) {
                    if(password.equals(passwordCheck)){

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startToast("회원가입을 성공하였습니다.");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            profileUpdate();
                                            loginPageIntent();
                                            finish();
                                        } else {
                                            if(task.getException() !=null) {
                                                startToast(task.getException().toString());
                                            }
                                        }
                                    }
                                });
                    } else {
                        startToast("비밀번호를 확인해주세요 .");
                    }
                }else{
                    startToast("비밀번호를 6자리 이상입력해주세요 .");
                }
            }else{
                startToast("이메일 형식이 올바르지않습니다.");
            }
        }else{
            startToast("이름과 닉네임을 입력해주세요.");
        }



    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void loginPageIntent(){
        Intent intent = new Intent(SignUp.this,Login.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

