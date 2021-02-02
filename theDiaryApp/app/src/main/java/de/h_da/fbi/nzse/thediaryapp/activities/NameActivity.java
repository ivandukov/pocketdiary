package de.h_da.fbi.nzse.thediaryapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.activities.tutorial.TutorialActivity;
import de.h_da.fbi.nzse.thediaryapp.R;

public class NameActivity extends AppCompatActivity {


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        SharedPreferences prefsa = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

         //Text field to insert customs name
        TextInputEditText editTextName = findViewById(R.id.textInputEditTextNameIntro);

        ImageButton imgBtnNext = findViewById(R.id.btnNext);


        imgBtnNext.setOnClickListener(view -> {
            if (!Objects.requireNonNull(editTextName.getText()).toString().isEmpty() && editTextName.getText().toString().length() <= 20) {
                startActivity(new Intent(NameActivity.this, TutorialActivity.class));
                userNameHandler(prefsa, editTextName);
            }
            else{
                Toast.makeText(this, "Bitte gebe einen güligten Benutzernamen an.", Toast.LENGTH_SHORT).show();
            }
        });



    }

    /**
     * Writes the Username to SharedPreferences
     * @param prefsa - Shared Preferences
     * @param editTextName - EditText which has the Username in it
     */
    private void userNameHandler(SharedPreferences prefsa, EditText editTextName) {
        SharedPreferences.Editor edit = prefsa.edit();
        edit.putString("Name",editTextName.getText().toString());
        edit.apply();
    }
}