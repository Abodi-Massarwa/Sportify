package com.example.sportify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private Button signUP_button;
    private EditText first_edit,last_edit,email_edit, area_edit, phoneNumber_edit, password_edit,confirm_edit;
    FirebaseAuth ref=FirebaseAuth.getInstance();
    private User customer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signUP_button = (Button)findViewById(R.id.userbtn);

        first_edit =  (EditText) findViewById(R.id.first_edit);
        last_edit =  (EditText) findViewById(R.id.last_edit);
        email_edit = (EditText) findViewById(R.id.email_edit);
        area_edit =  (EditText) findViewById(R.id.area_edit);
        phoneNumber_edit =  (EditText) findViewById(R.id.phone_edit);
        password_edit=  (EditText) findViewById(R.id.password_edit);
        confirm_edit =  (EditText) findViewById(R.id.confirm_edit);



        signUP_button.setOnClickListener(v -> {
            customer = new User(first_edit.getText().toString().trim(), last_edit.getText().toString().trim(), area_edit.getText().toString().trim(), phoneNumber_edit.getText().toString().trim(), email_edit.getText().toString().trim());

            String email_text = email_edit.getText().toString().trim();
            String password_text = password_edit.getText().toString().trim();
            String confirmPassword_text = confirm_edit.getText().toString().trim();

            // check validity!
            if(!password_text.equals(confirmPassword_text)) {
                password_edit.setError("password and confirm password should match");
                return;
            }
            //if email already exist
            ref.fetchSignInMethodsForEmail(email_text).addOnCompleteListener((OnCompleteListener<SignInMethodQueryResult>) task -> {
                if(!task.getResult().getSignInMethods().isEmpty())
                {
                    email_edit.setError("email is already exist");
                    return;
                }
            });

            // sign up and add user details to the firebase..
            ref.createUserWithEmailAndPassword(email_text, password_text).addOnCompleteListener(signUp_progress -> {
                if (signUp_progress.isSuccessful()) {
                    DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("customers");
                    ref1.child(ref.getCurrentUser().getUid()).setValue(customer);
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class); //move to main menu activity
                    startActivity(myIntent);
                }
            });

        });
    }


}