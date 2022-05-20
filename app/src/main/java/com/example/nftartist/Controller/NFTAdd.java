package com.example.nftartist.Controller;

import static com.example.nftartist.Model.Util.INTENT_PATH;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nftartist.Model.NFTMain;

import com.example.nftartist.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class NFTAdd extends AppCompatActivity {
    private static final String TAG ="NFT";
    private ImageView freeview,addimage;
    private String nfturi,nftEx,nftPath,nftTitle;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    Boolean isUploadCheck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nftadd);

        freeview = (ImageView) findViewById(R.id.freeview);
        addimage = (ImageView) findViewById(R.id.addimageview);
        findViewById(R.id.main_exit).setOnClickListener(onClickListener);
        findViewById(R.id.upload).setOnClickListener(onClickListener);
        findViewById(R.id.freeview).setOnClickListener(onClickListener);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_exit:
                    loginPageIntent();
                    break;
                case R.id.upload:
                    NFTUpload();
                    break;
                case R.id.freeview:
                    permissionCheck();
                    break;
            }
        }

    };

    private void permissionCheck(){
        String permissionList[] = {Manifest.permission.CAMERA
                ,Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (ContextCompat.checkSelfPermission(
                this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissionList, 1);
            ActivityIntent();
            Log.e("클릭4","클릭4");

        } else {
            requestPermissions(permissionList, 1);
            startToast("권한을 허용해주십오");
            Log.e("클릭4","클릭4");
        }

    }

    private void NFTUpload() {

        if (user != null) {
            final String nftTitle = ((EditText) findViewById(R.id.addNftTitle)).getText().toString().trim();
            final String nftEx = ((EditText) findViewById(R.id.addNftEx)).getText().toString().trim();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();

                    final StorageReference mountainImagesRef = storageRef.child("users/" + "nft/" + user.getUid() + user.getIdToken(true));
                    final RelativeLayout loaderLayout = findViewById(R.id.loaderLyaout);

                    loaderLayout.setVisibility(View.VISIBLE);
                    if(isUploadCheck == true) {
                        if (nftTitle.length()>1) {
                            if (nftEx.length()>5) {
                                if (nftPath == null) {
                                    NFTMain nft = new NFTMain(nftTitle, nftEx, user.getUid());
                                    Uploader(nft);
                                } else {
                                    try {
                                        InputStream stream = new FileInputStream(new File(nftPath));
                                        UploadTask uploadTask = mountainImagesRef.putStream(stream);
                                        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    throw task.getException();
                                                }
                                                return mountainImagesRef.getDownloadUrl();
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {

                                                if (task.isSuccessful()) {
                                                    loaderLayout.setVisibility(View.GONE);
                                                    Uri downloadUri = task.getResult();
                                                    NFTMain nft = new NFTMain(nftTitle, nftEx, user.getUid(), downloadUri.toString());
                                                    Uploader(nft);

                                                } else {
                                                    loaderLayout.setVisibility(View.GONE);
                                                    startToast("업로드에 실패하였습니다.");
                                                }
                                            }

                                        });
                                    } catch (FileNotFoundException e) {
                                        Log.e("로그", "에러: " + e.toString());
                                    }

                                }
                            }else{
                                startToast("설명란에 5자이상 입력해주세요");
                                loaderLayout.setVisibility(View.GONE);
                            }
                        }else{
                            startToast("제목을 입력해주세요");
                            loaderLayout.setVisibility(View.GONE);
                        }
                    }else{
                        startToast("NFT 이미지를 추가해주세요");
                        loaderLayout.setVisibility(View.GONE);
                    }
        } else {
            startToast("회원정보를 입력해주세요.");
        }
    }



    private void Uploader(NFTMain nft) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("NFT")
                .add(nft)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        startToast("갤러리 등록을 성공하였습니다.");
                        isUploadCheck = true;
                        finish();
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("갤러리 등록에 실패하였습니다.");
                        isUploadCheck = true;
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    nftPath = data.getStringExtra(INTENT_PATH);
                    Glide.with(this).load(nftPath).centerCrop().override(500).into(freeview);
                    addimage.setVisibility(View.GONE);
                    isUploadCheck = true;
                }
                break;
            }
        }
    }

    private void loginPageIntent(){
        Intent intent = new Intent(NFTAdd.this,MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void ActivityIntent(){
        Intent intent = new Intent(NFTAdd.this, GalleryActivity.class);
        Log.e("클릭4","클릭4");
        startActivityForResult(intent,0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("종료4","종료4");
        finish();
    }
}