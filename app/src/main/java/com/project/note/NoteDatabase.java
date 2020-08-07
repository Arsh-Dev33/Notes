package com.project.note;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Notes.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };


    public static class populateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

                public populateDbAsyncTask(NoteDatabase db){
                    noteDao = db.noteDao();
                }

        @Override
        protected Void doInBackground(Void... voids) {
                    noteDao.insert(new Notes("title 1","decs 1",1));
                    noteDao.insert(new Notes("title 2","decs 2",4));
                    noteDao.insert(new Notes("title 3","decs 3",2));
                    noteDao.insert(new Notes("title 4","decs 4",3));
            return null;
        }
    }

}
