package de.h_da.fbi.nzse.thediaryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import de.h_da.fbi.nzse.thediaryapp.adapters.AchievementsAdapter;
import de.h_da.fbi.nzse.thediaryapp.models.Achievement;
import de.h_da.fbi.nzse.thediaryapp.models.RoomDatabase;
import de.h_da.fbi.nzse.thediaryapp.R;

public class AchievementsActivity extends AppCompatActivity {

    private List<Achievement> achievementList;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeScreenActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        RoomDatabase db = RoomDatabase.getDatabase(this);

        ImageButton imageButtonHome = findViewById(R.id.imageButtonHomeAch);

        imageButtonHome.setOnClickListener(v -> startActivity(new Intent(this, HomeScreenActivity.class)));

        try {
            achievementList = db.achievementDao().getAllAchievements(); //dirty query
        }
        catch (Exception e){
            Log.d("exception",e.getLocalizedMessage());
            Toast.makeText(this, "Error Retrieving Database", Toast.LENGTH_SHORT).show();
        }


        RecyclerView recyclerView = findViewById(R.id.achRecyclerView);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this); // this = context


        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AchievementsAdapter(achievementList,this));


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

        if (android.R.id.home == id) {
            onBackPressed();
        }
        if (R.id.settingsButtonTopBar == id) {

            Intent intent = new Intent(AchievementsActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }




}

