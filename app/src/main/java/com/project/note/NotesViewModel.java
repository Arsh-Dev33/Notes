package com.project.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Notes>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public void insert(Notes notes){
        noteRepository.insert(notes);
    }

    public void update(Notes notes){
        noteRepository.update(notes);
    }

    public void delete(Notes notes){
        noteRepository.delete(notes);
    }

    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Notes>> getAllNotes() {

        return allNotes;

    }
}
