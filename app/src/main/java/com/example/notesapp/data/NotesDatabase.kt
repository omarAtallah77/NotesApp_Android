package com.example.notesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.NoteModel
import kotlin.concurrent.Volatile

@Database(entities = [NoteModel::class] , version = 1 , exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun NoteDAO (): NotesDAO


    companion object {
        @Volatile
        var instance : NotesDatabase ? = null

        fun getInstance (context : Context) : NotesDatabase {
            return instance ?: synchronized(this)
            {
                val tempinstance = Room.databaseBuilder(
                context.applicationContext ,
                NotesDatabase::class.java ,
                "Notes_database"
            ).build()
                instance = tempinstance
                tempinstance

        }
        }
    }
}