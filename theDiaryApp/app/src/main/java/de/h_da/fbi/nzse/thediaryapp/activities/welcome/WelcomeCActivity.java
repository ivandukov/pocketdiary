package de.h_da.fbi.nzse.thediaryapp.activities.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.R;

public class WelcomeCActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_c);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageButton imgBtnNext = findViewById(R.id.btnNext);
        imgBtnNext.setOnClickListener(view -> startActivity(new Intent(WelcomeCActivity.this, WelcomeDActivity.class)));


        TextView txtBtnBack = findViewById(R.id.txtBtnBackwards);
        txtBtnBack.setOnClickListener(view -> startActivity(new Intent(WelcomeCActivity.this, WelcomeBActivity.class)));


    }




}