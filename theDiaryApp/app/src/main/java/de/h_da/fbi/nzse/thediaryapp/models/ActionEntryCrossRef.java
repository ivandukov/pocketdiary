package de.h_da.fbi.nzse.thediaryapp.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import java.io.Serializable;

/**
 * Entity which helps for the N:M Beziehung
 */
@Entity(primaryKeys = {"entry_id","action_id"}, foreignKeys = {
        @ForeignKey(entity = Entry.class,parentColumns = "entry_id",childColumns = "entry_id",onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Action.class,parentColumns = "action_id",childColumns = "action_id",onDelete = ForeignKey.CASCADE)
})
public class ActionEntryCrossRef implements Serializable {
    private long entry_id;
    private long action_id;

    public ActionEntryCrossRef(){}

    @Ignore
    public ActionEntryCrossRef(long entry_id, long action_id) {
        this.entry_id = entry_id;
        this.action_id = action_id;
    }


    public long getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(long entry_id) {
        this.entry_id = entry_id;
    }

    public long getAction_id() {
        return action_id;
    }

    public void setAction_id(long action_id) {
        this.action_id = action_id;
    }
}
