package com.example.smartcampus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.smartcampus.authentication.LoginActivity2;
import com.example.smartcampus.ebook.EbookActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class UserActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser ;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;

    private final String CHECKEDITEM = "checked_item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        FirebaseMessaging.getInstance().subscribeToTopic("notification");
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        switch (getCheckedItem()){
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
        currentUser = mAuth.getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.frame_layout);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

         drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
         navigationView = (NavigationView) findViewById(R.id.nav_view);
         toggle = new ActionBarDrawerToggle(
                this, drawer,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.admin_menu:
                startActivity(new Intent(UserActivity.this,LoginActivity.class));
                break;
            case R.id.logout_menu:
                mAuth.signOut();
                openLogin();
                break;
        }
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openLogin() {
        startActivity(new Intent(UserActivity.this, LoginActivity2.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            openLogin();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_rate:
                Uri uri6 = Uri.parse("https://play.google.com/store/apps/details?id=com.bytesoft.smartcampus&fbclid=IwAR08rC0PYCuDeWiOi0UfnNjgpkelcAc49ncHbPpFt4uqqFit5xPlpu1blvM");
                Intent i = new Intent(Intent.ACTION_VIEW,uri6);
                try {
                    startActivity(i);
                }
                catch (Exception e){
                    Toast.makeText(this, "Unable to open\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.navigation_issue:
                startActivity(new Intent(this, Management_Issue.class));
                break;
            case R.id.navigation_video_lectures:
                startActivity(new Intent(this, Video_Lecture.class));
                break;

            case R.id.navigation_hall:
                startActivity(new Intent(this, Hall.class));
                break;

            case R.id.navigation_website:
                Uri uri = Uri.parse("https://daffodilvarsity.edu.bd/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                break;
            case R.id.navigation_developer:
                Uri uri2 = Uri.parse("https://github.com/ArifAhmed120829");
                startActivity(new Intent(Intent.ACTION_VIEW,uri2));
                break;
            case R.id.navigation_portal:
                Uri uri3 = Uri.parse("http://studentportal.diu.edu.bd/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri3));
                break;
            case R.id.navigation_result:
                Uri uri4 = Uri.parse("http://studentportal.diu.edu.bd/#/result");
                startActivity(new Intent(Intent.ACTION_VIEW,uri4));
                break;
            case R.id.navigation_ebook:
                startActivity(new Intent(this, EbookActivity.class));
                break;

            case R.id.navigation_color:
                showDialog();
                break;
        }
        return true;
    }

    private void showDialog() {
        final String[] themes = this.getResources().getStringArray(R.array.theme);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Select Theme");
        builder.setSingleChoiceItems(R.array.theme, getCheckedItem(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected = themes[which];
                checkedItem = which;

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(selected == null){
                    selected = themes[which];
                    checkedItem = which;
                }
                switch (selected){
                    case "System Default":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        break;
                    case "Dark":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case "Light":
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
                setCheckedItem(checkedItem);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private int getCheckedItem(){
        return sharedPreferences.getInt(CHECKEDITEM,0);
    }
    private void setCheckedItem(int i){
        editor.putInt(CHECKEDITEM,i);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }
}