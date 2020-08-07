package com.project.note;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Notes notes);

    @Delete
    void delete(Notes notes);

    @Update
    void update(Notes notes);

    @Query("SELECT * FROM note_table ORDER BY PRIORITY DESC")
    LiveData<List<Notes>> getAllNotes();

    @Query("DELETE FROM note_table")
    void deleteAllNotes();
}
