package com.example.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    fun insert(noteEntity : NoteEntity)

    @Update
    fun update(noteEntity : NoteEntity)

    @Delete
    fun delete(noteEntity : NoteEntity)

    @Query("Select * from noteentity")
    fun getAll() : List<NoteEntity>

}