package com.example.sportify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

import com.example.sportify.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    public static  final int PERMISSION_REQ_CODE = 5;
    private  static final int PICK_IMAGE_REQ_CODE = 1;
    private Uri ImageUri ;
    private ActivityProfileBinding binding;

    private String fullname;
    private String Address ;
    private String email;
    private String mobile;
    private String URI;
    private FirebaseAuth firebase = FirebaseAuth.getInstance();
    private DatabaseReference ref;
    private SharedPreferences sp ;
    private SharedPreferences.Editor edit ;
    private FirebaseUser user ;
    private Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("customers/" + Objects.requireNonNull(firebase.getCurrentUser().getUid()));
       // sp = getSharedPreferences("Emails",MODE_PRIVATE);
        String Email = user.getEmail();
        String name = user.getDisplayName();
        String mobile = user.getPhoneNumber();
//        ref = FirebaseDatabase.getInstance().getReference("Profiles/"+ Objects.requireNonNull(firebase.getCurrentUser()).getUid() +"/" + Email);
  //      ref.child(firebase.getCurrentUser().getUid()).setValue(URI);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.profileEmailId.setText(Email);
        binding.profileEmailId.setEnabled(false);
        binding.profileNameId.setText(name);
        binding.profileMobileId.setText(mobile);
        fullname = binding.profileNameId.getText().toString();
        Address = binding.profileAddressId.getText().toString();
        mobile = binding.profileMobileId.getText().toString();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 URI = snapshot.child("Profile Photo").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


      // image = Uri.parse(URI);
        binding.profileImageId.setImageURI(image);
        binding.profileImageId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in,PICK_IMAGE_REQ_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ_CODE && resultCode == RESULT_OK){
            if(data != null){
                ImageUri = data.getData();
                binding.profileImageId.setImageURI(ImageUri);
                ref.child("Profile Photo").setValue(ImageUri.toString());
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

}