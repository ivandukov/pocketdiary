package de.h_da.fbi.nzse.thediaryapp.daos;

import androidx.room.Dao;
import androidx.room.Insert;

import de.h_da.fbi.nzse.thediaryapp.models.Mood;
@Dao
public interface MoodDao {
    @Insert
    void insert(Mood... mood);

}
