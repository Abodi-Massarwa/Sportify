package com.example.sportify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sportify.tools.Category;
import com.example.sportify.tools.Product;
import com.example.sportify.tools.User;
import com.example.sportify.tools.UserDataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PasswordActivity extends AppCompatActivity {
    private Button button;
    private EditText e1;
    private EditText e2;
    private List<Category> Category;
    private User user;
    /**/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        e1 = (EditText) findViewById(R.id.pass1);
        e2 = (EditText) findViewById(R.id.pass2);
        button = (Button) findViewById(R.id.finish);  /* sign up button*/
        //FirebaseDatabase data = FirebaseDatabase.getInstance();
        // DatabaseReference myRef = data.getReference();
//        try {
//            User received = (User) getIntent().getSerializableExtra("User");
                user = UserDataHolder.s_user;
//        }
//        catch (Exception e)
//        {
//
//        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // we should transfer the user data to set the password
                if (e1.getText().toString().equals(e2.getText().toString())) {
                    Toast.makeText(PasswordActivity.this, "Welcome to Sportify!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PasswordActivity.this, CategoryActivity.class);
                    // Intent i = getIntent();
                    //user = (User)i.getSerializableExtra("User");
                    // user.setPassowrd(e1.getText().toString());
                    //myRef.child("Users").setValue(user);
                    /*
                    TODO: info verification for duplicates , existing users .... , or illegal passwords or names
                    TODO requires firebase attachment firstly






                     */





//                    fauth=FirebaseAuth.getInstance();
//                    fauth.createUserWithEmailAndPassword(user.getName(),user.getEmail()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful())
//                                Toast.makeText(PasswordActivity.this,"Registeration successfull",Toast.LENGTH_LONG);
//                            else  Toast.makeText(PasswordActivity.this,"Registeration not successfull",Toast.LENGTH_LONG);
//                        }
//                    });











                    startActivity(intent);
                } else {
                    Toast.makeText(PasswordActivity.this, "Please put same password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    private void verify_info() {
//        if (i.getExtras() != null) {
//            user = (User) i.getExtras().getSerializable("user"); // casting the data back to User since User implements Serializable interface
//            ///
//
//
//            ////
//            final DatabaseReference RootRef;
//            RootRef = FirebaseDatabase.getInstance().getReference();
//            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    if (!(dataSnapshot.child("Users").child(user.getNumber()).exists())) {
//                        HashMap<String, Object> userdataMap = new HashMap<>();
//                        userdataMap.put("phone", user.getNumber());
//                        userdataMap.put("password", user.getPassword());
//                        userdataMap.put("name", user.getName());
//
//                        RootRef.child("Users").child(user.getNumber()).updateChildren(userdataMap);
//
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
    //    }
 //   }
}