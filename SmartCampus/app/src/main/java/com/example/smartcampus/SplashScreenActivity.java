package com.example.smartcampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    // Widgets
    private Button btn;
    private TextView t1,t2;
    private Animation animate_btn,animate_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        btn = findViewById(R.id.Getstartbtn);
        t1  = findViewById(R.id.welcome_txtview);
        t2  = findViewById(R.id.welcome_txtview2);

        // Now let's add the animation
        animate_btn = AnimationUtils.loadAnimation(this,
                R.anim.animate_btn);
        animate_txt = AnimationUtils.loadAnimation(this,
                R.anim.animate_texts);

        btn.setAnimation(animate_btn);

        // Let's Create animation for the text
        t1.setAnimation(animate_txt);
        t2.setAnimation(animate_txt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this,UserActivity.class));
                finish();
            }
        });

    }
}