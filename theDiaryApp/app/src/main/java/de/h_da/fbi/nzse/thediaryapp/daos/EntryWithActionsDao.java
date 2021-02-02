package de.h_da.fbi.nzse.thediaryapp.daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import de.h_da.fbi.nzse.thediaryapp.models.EntryWithActions;

@Dao
public interface EntryWithActionsDao {
    @Transaction
    @Query("SELECT * from Entry")
    List<EntryWithActions> entrywithActions();

}
