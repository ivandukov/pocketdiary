package de.h_da.fbi.nzse.thediaryapp.activities.entry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import de.h_da.fbi.nzse.thediaryapp.activities.HomeScreenActivity;
import de.h_da.fbi.nzse.thediaryapp.models.Entry;
import de.h_da.fbi.nzse.thediaryapp.models.RoomDatabase;
import de.h_da.fbi.nzse.thediaryapp.R;

public class NewEntryActivity extends AppCompatActivity {

    private TextView txtDate;
    private ImageView selectedMood;
    private int unselectedMood;
    private RoomDatabase db;
    private LocalDateTime localDateTime;
    private Entry entry;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeScreenActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        db = RoomDatabase.getDatabase(this);


        // if emoji-button get clicked, it changes the look

        ImageButton imgbtnGood3 = findViewById(R.id.btnGood3);
        imgbtnGood3.setOnClickListener(view -> isSelectedHandler(view,
                imgbtnGood3, R.drawable.ic_sentiment_laughing_filled_24px,
                R.drawable.ic_sentiment_laughing_outline_24px, 1));

        ImageButton imgbtnGood2 = findViewById(R.id.btnLove);
        imgbtnGood2.setOnClickListener(view -> isSelectedHandler(view,
                imgbtnGood2, R.drawable.ic_sentiment_very_happy_filled_24px,
                R.drawable.ic_sentiment_very_happy_24px_outline, 2));

        ImageButton imgbtnGood1 = findViewById(R.id.btnFFood);
        imgbtnGood1.setOnClickListener(view -> isSelectedHandler(view,
                imgbtnGood1, R.drawable.ic_sentiment_happy_filled_24px,
                R.drawable.ic_sentiment_happy_outlined_24px, 3));

        ImageButton imgbtnBad3 = findViewById(R.id.btnBasketball);
        imgbtnBad3.setOnClickListener(view -> isSelectedHandler(view,
                imgbtnBad3, R.drawable.ic_sentiment_neutral_24px,
                R.drawable.neutral_face_outline, 4));

        ImageButton imgbtnBad2 = findViewById(R.id.btnHome);
        imgbtnBad2.setOnClickListener(view -> isSelectedHandler(view,
                imgbtnBad2, R.drawable.ic_sentiment_sad_filled_24px,
                R.drawable.ic_sentiment_sad_outlined_24px, 5));

        ImageButton imgbtnBad1 = findViewById(R.id.btnFriends);
        imgbtnBad1.setOnClickListener(view -> isSelectedHandler(view,
                imgbtnBad1, R.drawable.ic_sentiment_very_unhappy_filled_24px,
                R.drawable.ic_sentiment_very_unhappy_outline_24px, 6));


        // if you want to return  < to the last page
        TextView txtBtn = findViewById(R.id.txtBtnBackwards);
        txtBtn.setOnClickListener(view -> onBackPressed());


        // next button to get to the next question and to initialize the mood table

        ImageButton btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(view -> insertMoodHandler());

        txtDate = findViewById(R.id.txtDate);
        txtDate.setOnClickListener(view -> pickDate());

        localDateTime = LocalDateTime.now(); // automatically sets to the current date.
        txtDate.setText(localDateTime.toLocalDate().toString());
    }

    /**
     * Inserts a mood into the entry table.
     */
    private void insertMoodHandler() {
        if (selectedMood != null) {
            RoomDatabase.databaseExecutor.execute(() -> {
                db.entryDao().insert(entry);
                Log.d("Entry", "New Entry Inserted Into Database");
            });
            startActivity(new Intent(NewEntryActivity.this, NewEntryActionActivity.class));

        } else {
            Toast.makeText(this, "Please select a mood.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * makes the Imagebuttons function like radio buttons
     *
     * @param inputView       an object that sets the status of the button (selected/unselected)
     * @param inputImgBtn     a reference to the imagebutton-object
     * @param selectedImage   an int identifying the image for the selected mood-icon
     * @param unselectedImage an int identifying the image for the unselected mood-icon
     * @param id              an int identifying, which mood was selected
     */
    private void isSelectedHandler(View inputView, ImageButton inputImgBtn, int selectedImage, int unselectedImage, int id) {

        if (selectedMood != null) // if the last selected Mood "exists"
        {
            selectedMood.setImageResource(unselectedMood); // unselecect it
        }

        inputImgBtn.setImageResource(selectedImage);
        inputView.setSelected(true);

        selectedMood = inputImgBtn; // remember last selected mood image
        unselectedMood = unselectedImage; // remember its unselection image

        entry = new Entry(localDateTime, id, null); // create a new object for database
    }



    /**
     * Opens a Calendar ( Material date Picker )  which is used to handle our dates.
     * Normally the date is automatically set to the current day, but when the user wants to change it to a later/earlier date,
     * it can be done
     */
    private void pickDate() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker picker = builder.build();

        picker.addOnPositiveButtonClickListener(dateLong -> {
            Date date = new Date();
            date.setTime((Long) dateLong);
            txtDate.setText(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
            localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); // overwrites the LocalDateTime
        });

        picker.show(getSupportFragmentManager(), picker.toString());
    }



}
