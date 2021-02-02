package de.h_da.fbi.nzse.thediaryapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Class which represents one user activity.
 * Example of an Action : Sleep, Love, Play Games ect.
 */
@Entity
public class Action implements Serializable {
    @PrimaryKey
    private long action_id;

    private String name;
    @ColumnInfo(name = "selected_image")
    private int selectedImage;

    public Action() {
    }

    public long getAction_id() {
        return action_id;
    }

    public void setAction_id(long action_id) {
        this.action_id = action_id;
    }

    @Ignore
    public Action(long action_id, String name, int selected_image) {
        this.action_id = action_id;
        this.name = name;
        this.selectedImage = selected_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }

}
