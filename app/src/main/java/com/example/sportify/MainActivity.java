package com.example.sportify;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sportify.ui.AdminActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button sign_in_button;
    Button sign_up_button;
    EditText email_edit;
    EditText pass_edit;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button AdminButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // login activity

        email_edit =  findViewById(R.id.email_edit);
        pass_edit = findViewById(R.id.password) ;
        sign_in_button =findViewById(R.id.signin);
        sign_up_button = findViewById(R.id.signup);
        AdminButton = findViewById(R.id.admin);
        sign_in_button.setOnClickListener(view ->{
            String email_text = email_edit.getText().toString().trim();
            String password_text = pass_edit.getText().toString().trim();

            auth.signInWithEmailAndPassword(email_text,password_text).addOnCompleteListener(login ->{
                if(login.isSuccessful()) {
                    Intent myIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(myIntent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong email or password",Toast.LENGTH_LONG).show();
                }

            });
        });
        
        sign_up_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
        });

    }

    public void contact_us(View view) {
        Intent intent= new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 0524472633"));
        startActivity(intent);
    }
    // in xml file of mainactivity we had attribute called onClick= "admin_login" he knows that he will call this method
    // in MainActivity class
    public void admin_login(View view) {
        /*
        let's validate if the desired user is really an admin
         */
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference dbRef=db.getReference("Admins");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    if(snap.getValue().equals(email_edit.getText().toString()))
                    {

                        auth.signInWithEmailAndPassword(email_edit.getText().toString(),pass_edit.getText().toString()).addOnCompleteListener(login -> {
                            if (login.isSuccessful()) {
                                Intent it = new Intent(MainActivity.this, AdminActivity.class);
                                startActivity(it);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                            }

                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "You're not an admin !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}