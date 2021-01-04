package com.example.sportify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportify.databinding.ActivityCategoryBinding;
import com.example.sportify.tools.Category;
import com.example.sportify.ui.AdminActivity;
import com.example.sportify.ui.CartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
   private List<Category> Category;
   private Toolbar toolbar;
   private DrawerLayout drawer;
   private NavigationView navigationView;
   private FirebaseAuth auth;
   private FirebaseUser user;
   private ActivityCategoryBinding binding;
   private TextView name;
   private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        Category = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        Category.add(new Category("Dumbbells", "Category Sport", R.drawable.dumbelllogo));
        Category.add(new Category("Athlete", "Category Sport", R.drawable.basketball));
        Category.add(new Category("Electrics", "Category Sport", R.drawable.gym));
        Category.add((new Category("Clothes", "Category Sport", R.drawable.sportlogo)));
        Category.add((new Category("Boards", "Category Sport", R.drawable.skateboard)));
        Category.add(new Category("Tennis", "Category Sport", R.drawable.tennis));
        Category.add(new Category("Nike clothes", "Category Sport", R.drawable.nike));
        Category.add(new Category("Adidas clothes", "Category Sport", R.drawable.adidas));
        Category.add((new Category("Football", "Category Sport", R.drawable.ball)));
        Category.add((new Category("Best of fashion", "Category Sport", R.drawable.ny)));
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewerAdapter myAdapter = new RecyclerViewerAdapter(this, Category);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);
        name =  binding.navView.getHeaderView(0).findViewById(R.id.nav_view_name);
        name.setText(user.getDisplayName());
        email = binding.navView.getHeaderView(0).findViewById(R.id.navView_email);
        email.setText(user.getEmail());
        binding.bottomNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile_bottom:
                        Intent j = new Intent(CategoryActivity.this,ProfileActivity.class);
                        startActivity(j);
                        break;
                        case R.id.Shopping_cart:
                            Intent i = new Intent (CategoryActivity.this, CartActivity.class);
                            startActivity(i);
                            break;

                    case  R.id.explore:

                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              //  Intent i;
                switch (item.getItemId()) {
                    case R.id.log_out:
                      Intent  i = new Intent(CategoryActivity.this,MainActivity.class);
                      auth.signOut();
                        startActivity(i);
                        return true;
                    case R.id.profile:
                          Intent j = new Intent(CategoryActivity.this,ProfileActivity.class);
                        startActivity(j);
                        return true;
                    case R.id.add_product:
                        Intent L = new Intent(CategoryActivity.this, AdminActivity.class);
                        startActivity(L);
                        return true;

                }
                return false;
            }

        });

    }

   // @Override
  //  public void onBackPressed() {
    //    if (drawer.isDrawerOpen(GravityCompat.START)) {
           //drawer.closeDrawer(GravityCompat.START);
      //  }else {
        //    super.onBackPressed();
        //}
    ///}

}