package de.h_da.fbi.nzse.thediaryapp.activities.entry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.TreeMap;

import de.h_da.fbi.nzse.thediaryapp.models.ActionEntryCrossRef;
import de.h_da.fbi.nzse.thediaryapp.models.Entry;
import de.h_da.fbi.nzse.thediaryapp.models.RoomDatabase;
import de.h_da.fbi.nzse.thediaryapp.R;

public class NewEntryActionActivity extends AppCompatActivity {

    private RoomDatabase db;
    private Entry incomingEntry;
    private final TreeMap<Integer,ActionEntryCrossRef> actionEntryCrossRefSortedMap = new TreeMap<>();
    private static final int MAX_ACTIONS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry_action);


        db = RoomDatabase.getDatabase(this);

        ImageButton btnController = findViewById(R.id.btnGame);
        btnController.setOnClickListener(view -> isSelectedHandler(view, btnController, R.drawable.ic_sports_esports_24px, R.drawable.ic_sports_esports_outline_24px,1));

        ImageButton btnHeart = findViewById(R.id.btnLove);
        btnHeart.setOnClickListener(view -> isSelectedHandler(view, btnHeart, R.drawable.ic_favorite_24px, R.drawable.ic_favorite_border_24px,2));

        ImageButton btnFastFood = findViewById(R.id.btnFFood);
        btnFastFood.setOnClickListener(view -> isSelectedHandler(view, btnFastFood, R.drawable.ic_fastfood_24px, R.drawable.ic_fastfood_24px_1,3));

        ImageButton btnBasketball = findViewById(R.id.btnBasketball);
        btnBasketball.setOnClickListener(view -> isSelectedHandler(view, btnBasketball, R.drawable.ic_sports_basketball_24px_1_, R.drawable.ic_sports_basketball_24px,4));

        ImageButton btnCouch = findViewById(R.id.btnHome);
        btnCouch.setOnClickListener(view -> isSelectedHandler(view, btnCouch, R.drawable.ic_weekend_24px_1, R.drawable.ic_weekend_24px,5));

        ImageButton btnGroup = findViewById(R.id.btnFriends);
        btnGroup.setOnClickListener(view -> isSelectedHandler(view, btnGroup, R.drawable.ic_groups_24px, R.drawable.ic_groups_24px_1,6));

        ImageButton btnRadio = findViewById(R.id.btnMusic);
        btnRadio.setOnClickListener(view -> isSelectedHandler(view, btnRadio, R.drawable.ic_radio_24px, R.drawable.ic_radio_24px_outline_,7));

        ImageButton btnCart = findViewById(R.id.btnShopping);
        btnCart.setOnClickListener(view -> isSelectedHandler(view, btnCart, R.drawable.ic_shopping_cart_24px_1, R.drawable.ic_shopping_cart_24px,8));

        ImageButton btnWork = findViewById(R.id.btnBriefcase);
        btnWork.setOnClickListener(view -> isSelectedHandler(view, btnWork, R.drawable.ic_work_24px, R.drawable.ic_work_outline_24px,9));

        try {
            RoomDatabase.databaseExecutor.execute(() -> incomingEntry = db.entryDao().getLastId());
        }
        catch (Exception e){
            Log.d("exception",e.getLocalizedMessage());
            Toast.makeText(this, "Error Initializing Database", Toast.LENGTH_SHORT).show();
        }



        // next button to get to the next> question
        ImageButton btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(view -> addActionEntry());

        //if you want to return < to the last page
        TextView txtBtn = findViewById(R.id.txtBtnBackwards);
        txtBtn.setOnClickListener(view -> onBackPressed());

    }


    /**
     * Function which inserts the Actions into the database and goes to the note activity ( the next activity in the lifecycle)
     * It also serializes the Entry so that we can update it with the note, because the last part of our cycle is the note. The problem is that
     * we make a new entry earlier so that we can get the auto_incremented id, which we are using to insert into our cross reference ( M:N Beziehung ).
     */
    private void addActionEntry() {
        if(!actionEntryCrossRefSortedMap.isEmpty() && actionEntryCrossRefSortedMap.size() <= MAX_ACTIONS) {
            actionEntryCrossRefSortedMap.forEach((actionId, crossRefId) -> {
                RoomDatabase.databaseExecutor.execute(() -> db.actionEntryDao().insert(crossRefId));
                startActivity(new Intent(NewEntryActionActivity.this, NewEntryNoteActivity.class).putExtra("Entry",incomingEntry));
            });
        }
        else{
            Toast.makeText(this, "More than 3 actions have been chosen", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * makes the Imagebuttons function like radio buttons
     *
     * @param inputView       an object that sets the status of the button (selected/unselected)
     * @param inputImgBtn     a reference to the imagebutton-object
     * @param selectedImage   an int identifying the image for the selected mood-icon
     * @param unselectedImage an int identifying the image for the unselected mood-icon
     */
    private void isSelectedHandler(View inputView, ImageButton inputImgBtn, int selectedImage, int unselectedImage, int id) {

        if(inputView.isSelected())
        {
            inputImgBtn.setImageResource(unselectedImage);
            inputView.setSelected(false);
            actionEntryCrossRefSortedMap.remove(id);
            Log.d("Action", "Action has been DELETED FROM the Map!");
        }
        else if(!inputView.isSelected())
        {
            inputImgBtn.setImageResource(selectedImage);
            inputView.setSelected(true);
            actionEntryCrossRefSortedMap.put(id,new ActionEntryCrossRef(incomingEntry.getEntry_id(),id));
            Log.d("Action", "Action has been Inserted Into Map!");

        }

    }




}