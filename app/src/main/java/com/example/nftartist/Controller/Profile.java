package com.example.nftartist.Controller;

import static com.example.nftartist.Model.Util.INTENT_PATH;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nftartist.Model.userInfo;
import com.example.nftartist.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG ="Profile";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String name;
    private Fragment Profile;
    private String nicName;
    private String email;
    private String photoUrl;
    private String profilePath;
    private ImageView photo;
    private TextView info_name;
    private TextView info_Nicname;
    private TextView info_introduce;
    FirebaseUser user;
    FirebaseFirestore db;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Profile =new Profile();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra(INTENT_PATH);
                    Bitmap bmp = BitmapFactory.decodeFile(profilePath);
                    photo.setImageBitmap(bmp);
                    profileUpdate();
                }
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        photo = (ImageView) v.findViewById(R.id.Myinfoimage);
        info_Nicname = (TextView)v.findViewById(R.id.info_nicName);
        info_name = (TextView)v.findViewById(R.id.info_name);
        info_introduce = (TextView)v.findViewById(R.id.info_introduce);

        Button Button = (Button)v.findViewById(R.id.profile_change);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        String permissionList[] = {Manifest.permission.CAMERA
                ,Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUpdate();
            }
        });
            photo.setImageResource(R.drawable.avatar);

            Dataset();




        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (ContextCompat.checkSelfPermission(
                       getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(permissionList, 1);
                        ActivityIntent();
                        Log.e("클릭","클릭");

                    } else {
                        requestPermissions(permissionList, 1);
                        startToast("권한을 허용해주십오");
                   Log.e("클릭2","클릭2");
                    }

                }

        });
        return v;

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int res : grantResults){
            if(res == PackageManager.PERMISSION_DENIED){
                return;
            }else{
                ActivityIntent();
                Log.e("클릭1","클릭1");
            }
        }
    }
    private void startToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void ActivityIntent(){
        Intent intent = new Intent(getActivity(), GalleryActivity.class);
        Log.e("클릭3","클릭3");
        startActivityForResult(intent,0);

    }

    public void Dataset(){
        DocumentReference docRef = db.collection(user.getUid()).document("Profile");
        photoUrl = "0";
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             userInfo info = (userInfo) new userInfo(name,nicName,email,photoUrl);
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name = document.getData().get("name").toString().trim();
                        nicName = document.getData().get("nicName").toString().trim();

                        info_name.setText(name);
                        info_Nicname.setText(nicName);
                        photoUrl = document.getData().get("photoUrl").toString().trim();

                        if(photoUrl != null && !photoUrl.equals("0")){
                            Log.d("이미지",photoUrl);
                            Picasso.get().load(Uri.parse(photoUrl)).into(photo);
                        }

                        Log.d(TAG, "DocumentSnapshot data: " + name + nicName);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }



    private void profileUpdate() {
        if (user != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            final StorageReference mountainImagesRef = storageRef.child("users/" + "profileimage/"+user.getUid()+".jpg");

            try {
                InputStream stream = new FileInputStream(new File(profilePath));

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
                            Uri downloadUri = task.getResult();
                            photoUrl = downloadUri.toString();
                            Log.d("성공", "성공: " + downloadUri);
                            userInfo Info = new userInfo(name,nicName,email,photoUrl);
                            Log.d("성공", "성공: " + photoUrl);
                            db.collection(user.getUid()).document("Profile").update("photoUrl",photoUrl);

                        } else {
                            Log.d("log", ":실패");
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

