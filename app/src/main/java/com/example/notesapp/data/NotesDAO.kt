package com.example.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.model.NoteModel

@Dao
interface NotesDAO {
    @Insert
    suspend fun insert (note : NoteModel)

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getall(): kotlinx.coroutines.flow.Flow<List<NoteModel>>

    @Update
    suspend  fun update(note : NoteModel)
    @Delete
    suspend fun delete (note : NoteModel)
}