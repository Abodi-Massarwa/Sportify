package com.example.sportify;

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

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.sportify.tools.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
   private List<Category> Category;
   Toolbar toolbar;
   private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Category = new ArrayList<>();
        toolbar =(Toolbar)findViewById(R.id.toolbar);
        drawer =(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open
        ,R.string.navigation_drawer_close);
       Category.add(new Category("Dumbells","Category Sport",R.drawable.dumbelllogo));
        Category.add(new Category("Athlete","Category Sport" ,R.drawable.basketball));
        Category.add(new Category("Sport Equipments","Category Sport",R.drawable.gym));
        Category.add((new Category("Clothes","Category Sport",R.drawable.sportlogo)));
        Category.add((new Category("Boards","Category Sport",R.drawable.skateboard)));
        Category.add(new Category("Tennis","Category Sport",R.drawable.tennis));
        Category.add(new Category("Nike clothes","Category Sport" ,R.drawable.nike));
        Category.add(new Category("Adidas clothes","Category Sport",R.drawable.adidas));
        Category.add((new Category("Football","Category Sport",R.drawable.ball)));
        Category.add((new Category("Best of fashion","Category Sport",R.drawable.ny)));
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewerAdapter myAdapter = new RecyclerViewerAdapter(this,Category);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}