package de.h_da.fbi.nzse.thediaryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import de.h_da.fbi.nzse.thediaryapp.activities.entry.ViewEntryActivity;
import de.h_da.fbi.nzse.thediaryapp.models.EntryWithActions;
import de.h_da.fbi.nzse.thediaryapp.R;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder> {

    private final List<EntryWithActions> entryWithActions;
    private final Context context; // In what activity is our app currently using the recyclerview?

    public EntriesAdapter(List<EntryWithActions> entryWithActions, Context context) {
        this.entryWithActions = entryWithActions;
        this.context = context;
        if (entryWithActions != null) {
            entryWithActions.sort((o1, o2) -> o2.entry.getCreatedAt().compareTo(o1.entry.getCreatedAt()));
        }
    }

    @NonNull
    @Override
    public EntriesAdapter.EntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewholder_entry,parent,false);
        return new EntriesViewHolder(view);

    }

    /** binds the data (the actions) onto the views
     *
     * @param holder see EntriesViewHolder
     * @param position icons and data have the same index in container
     */
    @Override
    public void onBindViewHolder(@NonNull EntriesAdapter.EntriesViewHolder holder, int position) {

        if (entryWithActions != null) {
            EntryWithActions entryWithActionsObject = entryWithActions.get(position);

            if(entryWithActionsObject.actionList.size() > 0){
            holder.imageViewMood.setImageResource(entryWithActionsObject.mood.getImagePath());
            holder.textViewEntryDate.setText(entryWithActionsObject.entry.getCreatedAt().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            holder.textViewEntryNote.setText(entryWithActionsObject.entry.getNote());

            holder.constraintLayoutEntry.setOnClickListener(view -> { // Clickable Entry
                Intent intent = new Intent(context, ViewEntryActivity.class);
                intent.putExtra("EntryWithActions",entryWithActionsObject);
                context.startActivity(intent);
            });
            setImages(holder, entryWithActionsObject);
            }
        }
        else {
            Toast.makeText(context, "Error Retrieving Data", Toast.LENGTH_SHORT).show();
        }
    }

    /** sets the fitting images to the data portrayed in the view
     *
     * @param holder the view that "holds" the data
     * @param entryWithActionsObject the data that will be
     */
    private void setImages(@NonNull EntriesViewHolder holder, EntryWithActions entryWithActionsObject)
    {
        switch (entryWithActionsObject.actionList.size())
        {
            case 1: {
                holder.imageViewAction1.setImageResource(entryWithActionsObject.actionList.get(0).getSelectedImage());
                break;
            }
            case 2: {
                holder.imageViewAction1.setImageResource(entryWithActionsObject.actionList.get(0).getSelectedImage());
                holder.imageViewAction2.setImageResource(entryWithActionsObject.actionList.get(1).getSelectedImage());
                break;
            }
            case 3: {
                holder.imageViewAction1.setImageResource(entryWithActionsObject.actionList.get(0).getSelectedImage());
                holder.imageViewAction2.setImageResource(entryWithActionsObject.actionList.get(1).getSelectedImage());
                holder.imageViewAction3.setImageResource(entryWithActionsObject.actionList.get(2).getSelectedImage());
                break;
            }
            default: {
                Log.d("error", "error loading actions");
                Toast.makeText(context, "Error Loading Actions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return entryWithActions.size();
    }

    /**
     * This class is like a "pattern"/"template" which holds all the information,
     * we want to show in the recyclerview "per entry".
     */
    public static class EntriesViewHolder extends RecyclerView.ViewHolder{

        final ImageView imageViewMood,imageViewAction1,imageViewAction2,imageViewAction3;
        final TextView textViewEntryDate,textViewEntryNote;
        final ConstraintLayout constraintLayoutEntry;


        public EntriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMood = itemView.findViewById(R.id.imgViewMood);
            imageViewAction1 = itemView.findViewById(R.id.imgViewAction1);
            imageViewAction2 = itemView.findViewById(R.id.imgViewAction2);
            imageViewAction3 = itemView.findViewById(R.id.imgViewAction3);
            textViewEntryDate = itemView.findViewById(R.id.txtViewEntryDate);
            textViewEntryNote = itemView.findViewById(R.id.txtViewEntryText);
            constraintLayoutEntry = itemView.findViewById(R.id.viewHolderEntry);

        }
    }



}


