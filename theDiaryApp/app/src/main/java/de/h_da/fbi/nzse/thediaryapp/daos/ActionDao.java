package de.h_da.fbi.nzse.thediaryapp.daos;

import androidx.room.Dao;
import androidx.room.Insert;

import de.h_da.fbi.nzse.thediaryapp.models.Action;
@Dao
public interface ActionDao {
    @Insert
    void insert(Action... action);

}
