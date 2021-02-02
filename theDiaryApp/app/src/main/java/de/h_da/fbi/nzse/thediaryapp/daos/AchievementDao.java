package de.h_da.fbi.nzse.thediaryapp.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.h_da.fbi.nzse.thediaryapp.models.Achievement;
@Dao
public interface AchievementDao {
    @Insert
    void insert(Achievement... achievement);

    @Update
    void update(Achievement... achievement);

    @Query("SELECT * FROM Achievement")
    List<Achievement> getAllAchievements();

}
