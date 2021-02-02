package de.h_da.fbi.nzse.thediaryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.h_da.fbi.nzse.thediaryapp.models.Achievement;

import de.h_da.fbi.nzse.thediaryapp.R;


public class AchievementsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Achievement> achievementList;
    private final Context context;

    private static final int TYPE_GRAY = 1;
    private static final int TYPE_NORMAL = 2;

    public AchievementsAdapter(List<Achievement> achievementList, Context context) {
        this.achievementList = achievementList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(parent) {
        };


        switch (viewType){
            case 1:{
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.viewholder_achievements_grey,parent,false);
                return new AchievementsViewHolderGray(view);
            }
            case 2:{
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.viewholder_achievements,parent,false);
                return new AchievementsViewHolder(view);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_GRAY){
            ((AchievementsViewHolderGray) holder).setAchievementDetailsGray(achievementList.get(position));
        }
        else if (getItemViewType(position) == TYPE_NORMAL){
            ((AchievementsViewHolder) holder).setAchievementDetails(achievementList.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (achievementList.get(position).isDone()){
            return TYPE_NORMAL;
        }
        else{
            return TYPE_GRAY;
        }
    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }



    public static class AchievementsViewHolderGray extends RecyclerView.ViewHolder{

        final ImageView  imageViewGray;
        final TextView  textViewTitleGray,textViewBodyGray;


        public AchievementsViewHolderGray(@NonNull View itemView) {
            super(itemView);

            imageViewGray = itemView.findViewById(R.id.imgView14EntriesGrey);
            textViewBodyGray = itemView.findViewById(R.id.txtViewBodyGrey);
            textViewTitleGray = itemView.findViewById(R.id.txtViewTitleGrey);
        }

        private void setAchievementDetailsGray(Achievement achievement){
            imageViewGray.setImageResource(achievement.getAchievementImage());
            textViewBodyGray.setText(achievement.getDescription());
            textViewTitleGray.setText(achievement.getAchievementName());
        }

    }

    public static class AchievementsViewHolder extends RecyclerView.ViewHolder{

        final ImageView imageViewAch;
        final TextView textViewTitle,textViewBody;


        public AchievementsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAch = itemView.findViewById(R.id.imgView14EntriesAch);
            textViewBody = itemView.findViewById(R.id.txtViewBodyAch);
            textViewTitle = itemView.findViewById(R.id.txtViewTitleAch);
        }

        private void setAchievementDetails(Achievement achievement){
            imageViewAch.setImageResource(achievement.getAchievementImage());
            textViewBody.setText(achievement.getDescription());
            textViewTitle.setText(achievement.getAchievementName());
        }

    }


}
