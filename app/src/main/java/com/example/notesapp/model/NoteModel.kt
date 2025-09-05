package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
@Entity(tableName = "Notes")
data class NoteModel(
    val title : String ,
    val subtitle : String ,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0 ,
    val date : String)