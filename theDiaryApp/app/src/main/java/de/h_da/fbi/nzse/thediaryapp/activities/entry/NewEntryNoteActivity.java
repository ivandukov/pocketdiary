package de.h_da.fbi.nzse.thediaryapp.activities.entry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.activities.HomeScreenActivity;
import de.h_da.fbi.nzse.thediaryapp.models.Entry;
import de.h_da.fbi.nzse.thediaryapp.models.RequestWorker;
import de.h_da.fbi.nzse.thediaryapp.models.RoomDatabase;
import de.h_da.fbi.nzse.thediaryapp.R;

public class NewEntryNoteActivity extends AppCompatActivity {


    private Entry incomingEntry;
    private RoomDatabase db;
    private TextInputEditText textInputEditText;
    private static final int MAX_NOTE_CHARLENGHT = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry_note);


        // Entry which was serialized in NewEntryActionActivity
        incomingEntry = (Entry) getIntent().getSerializableExtra("Entry");


        textInputEditText = findViewById(R.id.noteEditText);
        db = RoomDatabase.getDatabase(this);


        //next button to get to the next > question

        FloatingActionButton btnSave = findViewById(R.id.btnImgSave);
        btnSave.setOnClickListener(view -> saveEntryToDatabase());

        // if you want to return  < to the last page

        TextView txtBtn = findViewById(R.id.txtBtnBackwards);
        txtBtn.setOnClickListener(view -> onBackPressed());


    }

    /**
     * Function which saves ( updates ) an entry to the database. It looks first if the note is not empty and is not more than
     * the MAX_NOTE-CHARLENGHT constant. The function also sends an AchievementWorker which is used to handle the Achievements.
     */
    private void saveEntryToDatabase() {
        if (Objects.requireNonNull(textInputEditText.getText()).toString().length() <= MAX_NOTE_CHARLENGHT) {
            incomingEntry.setNote(textInputEditText.getText().toString());
            startActivity(new Intent(NewEntryNoteActivity.this, HomeScreenActivity.class));

            checkAchievement();

            try {
                RoomDatabase.databaseExecutor.execute(() -> db.entryDao().update(incomingEntry));
            }
            catch (Exception e){
                Log.d("exception",e.getLocalizedMessage());
                Toast.makeText(this, "Error Updating Database", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "There's something wrong in the Note!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  Sends a Worker to handle the Achievements
     *  @see de.h_da.fbi.nzse.thediaryapp.models.RequestWorker
     */
    private void checkAchievement() {
        WorkRequest checkAchievements = new OneTimeWorkRequest.Builder(RequestWorker.class).build();
        WorkManager.getInstance(this).enqueue(checkAchievements);
    }

}