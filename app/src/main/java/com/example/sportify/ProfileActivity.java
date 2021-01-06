package com.example.sportify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sportify.databinding.ActivityProfileBinding;
import com.example.sportify.tools.Profile;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    public static  final int PERMISSION_REQ_CODE = 5;
    private Uri ImageUri ;
    private ActivityProfileBinding binding;
    private Profile user_profile;
    private String fullname;
    private String Address ;
    private String email;
    private String mobile;
    private String URI;
    private FirebaseAuth firebase = FirebaseAuth.getInstance();
    private DatabaseReference ref;
    private ImageView img;
    private SharedPreferences.Editor edit ;
    private FirebaseUser user;
    private Uri image;
    private StorageTask uploadTask;
    private StorageReference storageProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        }

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.list_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
        storageProfile = FirebaseStorage.getInstance().getReference().child("Profile Pics");
        StorageReference profileRef = storageProfile.child(firebase.getCurrentUser().getUid() +".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null)
           Picasso.get().load(uri).into(binding.profileImageId);
            }
        });
        img = (ImageView)findViewById(R.id.profile_image_id);
       // sp = getSharedPreferences("Emails",MODE_PRIVATE);
        String Email = user.getEmail();
        String name = user.getDisplayName();

        if (ref.child("Profiles").child(user.getUid())!=null) {
            ref.child("Profiles").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        user_profile = snapshot.getValue(Profile.class);
                        binding.profileEmailId.setText(user_profile.getEmail());
                        binding.profileMobileId.setText(user_profile.getNumber());
                        binding.profileNameId.setText(user_profile.getName());
                        binding.profileAddressId.setText(user_profile.getAddress());
                       // String image = user_profile.getImage_url();
                       // Picasso.get().load(image).into(binding.profileImageId);
                       // Glide.with(ProfileActivity.this).load(image).into(img);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        binding.profileEmailId.setText(Email);
        binding.profileEmailId.setTextColor(Color.BLACK);
        binding.profileEmailId.setEnabled(false);

        binding.saveBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = binding.profileNameId.getText().toString();
                Address = binding.profileAddressId.getText().toString();
                mobile = binding.profileMobileId.getText().toString();
                user_profile = new Profile(Email,fullname,mobile,ImageUri.toString(),Address);
        //        user_profile = new Profile(Email,fullname,mobile,ImageUri.toString(),Address);
                ref.child("Profiles").child(user.getUid()).setValue(user_profile);
                Snackbar.make(binding.saveBotton,"Profile Uploaded",Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.profileImageId.setImageURI(image);
        binding.profileImageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(in,100);
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK ){
            if(data != null){
                ImageUri =  data.getData();
                binding.profileImageId.setImageURI(ImageUri);
                uploadProfileImage();
            }else{
                Toast.makeText(this,"Error, Try Again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);;
        switch (requestCode){
            case  PERMISSION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else{

                }
        }
    }
    private void uploadProfileImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog .setTitle("Set Your Profile");
        progressDialog.setMessage("Please wait,until we uploading your data");
        progressDialog.show();
        if (ImageUri != null){
            final StorageReference fileRef = storageProfile
                    .child(firebase.getCurrentUser().getUid() + ".jpg");

            uploadTask = fileRef.putFile(ImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef;
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = (Uri) task.getResult();
                        URI = downloadUri.toString();
                        user_profile.setImage_url(URI);
                        HashMap<String,Object> userMap = new HashMap<>();
                        userMap.put("image",Profile.class);
                        ref.child("Profiles").child(user.getUid()).updateChildren(userMap);
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this,"Image not selected",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}