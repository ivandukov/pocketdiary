package de.h_da.fbi.nzse.thediaryapp.models;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

/**
 *  Class which converts LocalDateTime objects to string and vice-versa.
 *  This is needed because SQLlite does not support Date Objects
 */
public class Converter {

    @TypeConverter
    public static LocalDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDateTime.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
