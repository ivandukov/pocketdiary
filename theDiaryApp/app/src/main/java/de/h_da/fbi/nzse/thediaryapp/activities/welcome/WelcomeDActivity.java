package de.h_da.fbi.nzse.thediaryapp.activities.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.activities.NameActivity;
import de.h_da.fbi.nzse.thediaryapp.R;

public class WelcomeDActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_d);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageButton imgBtnNext = findViewById(R.id.btnNext);
        imgBtnNext.setOnClickListener(view -> startActivity(new Intent(WelcomeDActivity.this, NameActivity.class)));


        TextView txtBtnBack = findViewById(R.id.txtBtnBackwards);
        txtBtnBack.setOnClickListener(view -> startActivity(new Intent(WelcomeDActivity.this, WelcomeCActivity.class)));
    }
}