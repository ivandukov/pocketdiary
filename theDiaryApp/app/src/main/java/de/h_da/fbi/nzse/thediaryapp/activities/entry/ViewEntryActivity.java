package de.h_da.fbi.nzse.thediaryapp.activities.entry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;

import de.h_da.fbi.nzse.thediaryapp.activities.HomeScreenActivity;
import de.h_da.fbi.nzse.thediaryapp.activities.SettingsActivity;
import de.h_da.fbi.nzse.thediaryapp.models.EntryWithActions;
import de.h_da.fbi.nzse.thediaryapp.models.RoomDatabase;
import de.h_da.fbi.nzse.thediaryapp.R;

public class ViewEntryActivity extends AppCompatActivity {

    private EntryWithActions entryWithActions;
    private RoomDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        ImageView imageViewMoodOverview = findViewById(R.id.imgViewMoodEntryOverview);
        ImageView imageViewAction1Overview = findViewById(R.id.imgViewAction1EntryOverview);
        ImageView imageViewAction2Overview = findViewById(R.id.imgViewAction2EntryOverview);
        ImageView imageViewAction3Overview = findViewById(R.id.imgViewAction3EntryOverview);
        db = RoomDatabase.getDatabase(this);
        TextView textViewEntryDateOverview = findViewById(R.id.txtViewEntryDateOverview);
        TextView textViewEntryNoteOverview = findViewById(R.id.noteViewTextEntryOverview);

        ImageButton imgbtndelete = findViewById(R.id.imgbtnDelete);



        TextView txtBtn = findViewById(R.id.txtBtnBackwards);
        txtBtn.setOnClickListener(view -> startActivity( new Intent(ViewEntryActivity.this, HomeScreenActivity.class)));


        setImages(imageViewMoodOverview, imageViewAction1Overview, imageViewAction2Overview,
                  imageViewAction3Overview, textViewEntryDateOverview, textViewEntryNoteOverview,
                  imgbtndelete);
    }

    private void setImages(ImageView imageViewMoodOverview, ImageView imageViewAction1Overview,
                           ImageView imageViewAction2Overview, ImageView imageViewAction3Overview,
                           TextView textViewEntryDateOverview, TextView textViewEntryNoteOverview,
                           ImageButton imgbtndelete)
    {
        if (getIntent().hasExtra("EntryWithActions")) {

            entryWithActions = (EntryWithActions) getIntent().getSerializableExtra("EntryWithActions");


            imgbtndelete.setOnClickListener(v -> showAlertDialog());

            // Mood Icon, Eintragsdatum, Notiz setzen:
            imageViewMoodOverview.setImageResource(entryWithActions.mood.getImagePath());
            textViewEntryDateOverview.setText(entryWithActions.entry.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            textViewEntryNoteOverview.setText(entryWithActions.entry.getNote());

            // Je nach dem wv. Actions ausgewählt wurden, werden die Bilder gesetzt:
            switch (entryWithActions.actionList.size()) {
                case 1: {
                    imageViewAction1Overview.setImageResource(entryWithActions.actionList.get(0).getSelectedImage());
                    break;
                }
                case 2: {
                    imageViewAction1Overview.setImageResource(entryWithActions.actionList.get(0).getSelectedImage());
                    imageViewAction2Overview.setImageResource(entryWithActions.actionList.get(1).getSelectedImage());
                    break;
                }
                case 3: {
                    imageViewAction1Overview.setImageResource(entryWithActions.actionList.get(0).getSelectedImage());
                    imageViewAction2Overview.setImageResource(entryWithActions.actionList.get(1).getSelectedImage());
                    imageViewAction3Overview.setImageResource(entryWithActions.actionList.get(2).getSelectedImage());
                    break;
                }
                default: {
                    Log.d("error", "error loading actions");
                    Toast.makeText(this, "Error Loading Actions", Toast.LENGTH_SHORT).show();
                }

            }
        }
        else{
                Toast.makeText(this, "Could Not Load Data From HomeScreen", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(R.id.settingsButtonTopBar == id){
            Intent intent = new Intent(ViewEntryActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }

    /**
     * Shows an alert dialog which prompts the user to confirm that they really want to delete the said entry.
     */
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle("Eintrag Löschen");
        builder.setMessage("Bist du dir sicher, dass du dein Eintrag löschen möchtest?");
        builder.setPositiveButton("Ja", (dialog, which) -> deleteEntry());
        builder.setNegativeButton("Abbrechen", (dialog, which) -> {
        });
        builder.create().show();
    }
    /**
     * Deletes the said entry and goes to the homescreen.
     */

    private void deleteEntry() {
        RoomDatabase.databaseExecutor.execute(() -> db.entryDao().delete(entryWithActions.entry));
        Toast.makeText(ViewEntryActivity.this, "Dein Eintrag wurde erfolgreich gelöscht.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ViewEntryActivity.this, HomeScreenActivity.class));
    }
}