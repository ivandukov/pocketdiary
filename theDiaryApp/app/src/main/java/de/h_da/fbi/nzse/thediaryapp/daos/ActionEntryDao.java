package de.h_da.fbi.nzse.thediaryapp.daos;

import androidx.room.Dao;
import androidx.room.Insert;

import de.h_da.fbi.nzse.thediaryapp.models.ActionEntryCrossRef;

@Dao
public interface ActionEntryDao {
    @Insert
    void insert(ActionEntryCrossRef... actionEntries);

}
