package com.project.note;

import android.app.Application;
import android.os.AsyncTask;
import android.print.PrinterId;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Notes>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Notes notes){
        new InsertNoteAsyncTask(noteDao).execute(notes);
    }

    public void delete(Notes notes){
        new DeleteNotesAsyncTask(noteDao).execute(notes);
    }

    public void update(Notes notes){
        new UpdateNotesAsyncTask(noteDao).execute(notes);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Notes>> getAllNotes(){

        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Notes,Void,Void> {
        private NoteDao noteDao;

            private InsertNoteAsyncTask(NoteDao noteDao){
                this.noteDao = noteDao;
            }


        @Override
        protected Void doInBackground(Notes... notes) {

                noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNotesAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao noteDao;

            private DeleteNotesAsyncTask(NoteDao noteDao){
                this.noteDao = noteDao;
            }

        @Override
        protected Void doInBackground(Notes... notes) {
                noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class UpdateNotesAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao noteDao;

        private UpdateNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
