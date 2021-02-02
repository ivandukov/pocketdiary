package de.h_da.fbi.nzse.thediaryapp.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import de.h_da.fbi.nzse.thediaryapp.models.Entry;
@Dao
public interface EntryDao {
    @Insert
    void insert(Entry... entry);

    @Query("SELECT * FROM Entry ORDER BY entry_id DESC LIMIT 1;")
    Entry getLastId();

    @Update
    void update(Entry... entry);

    @Delete
    void delete(Entry... entry);
}
