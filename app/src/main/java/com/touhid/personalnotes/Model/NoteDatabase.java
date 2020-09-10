package com.touhid.personalnotes.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase
{
    //singalton
    private static NoteDatabase instance;

    //abstract method
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }

        return instance;
    }

    public static RoomDatabase.Callback callback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CreateRoomDBAsyncTask(instance).execute();
        }
    };

    public static class CreateRoomDBAsyncTask extends AsyncTask<Void, Void, Void>
    {
        NoteDao noteDao;

        public CreateRoomDBAsyncTask(NoteDatabase noteDatabase) {
            this.noteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            noteDao.insert(new Note("Title 1", "description"));
            noteDao.insert(new Note("Title 2", "description"));
            noteDao.insert(new Note("Title 3", "description"));
            return null;
        }
    }
}
