package com.example.sportify.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sportify.CategoryActivity;
import com.example.sportify.ProductDetails;
import com.example.sportify.R;
import com.example.sportify.tools.Category;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static com.example.sportify.ProfileActivity.PERMISSION_REQ_CODE;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText name;
    private EditText price;
    private Spinner details;
    private Button url;
    private EditText condition;
    private  static final int PICK_IMAGE_REQ_CODE =1;
    private Uri ImageUri ;
    private String Category;
    /*
     */
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference dbRef=db.getReference("Products");
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
         name=findViewById(R.id.product_name);
         price=findViewById(R.id.product_price);
         details=findViewById(R.id.product_details);
         url = findViewById(R.id.product_url);
         condition=findViewById(R.id.product_condition);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        details.setAdapter(adapter);
        details.setOnItemSelectedListener(this);
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in,PICK_IMAGE_REQ_CODE);
            }
        });

    }
    /*
    click listener for product adding in the admin's section
     */

    public void add_product(View view) {
        String id=dbRef.push().getKey();
        ProductDetails pd= new ProductDetails(name.getText().toString().trim(), Category, condition.getText().toString().trim(), price.getText().toString().trim(),id);
        pd.setImage_url(ImageUri.toString());
        dbRef.child(id).setValue(pd, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(AdminActivity.this, "Successfully added product", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminActivity.this, CategoryActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AdminActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ_CODE &&resultCode == RESULT_OK){
            if(data !=null){
                ImageUri = data.getData();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);;
        switch (requestCode){
            case  PERMISSION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    // set up the code here
                }else{

                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     Category = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}