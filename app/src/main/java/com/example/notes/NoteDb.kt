package com.example.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NoteEntity::class), version = 1)
abstract class NoteDb : RoomDatabase(){

    abstract fun noteDao() : NoteDao

    companion object{
        var noteDb : NoteDb ? = null

        fun getInstance(context: Context): NoteDb {
            if (noteDb == null){
                noteDb = Room.databaseBuilder(
                    context, NoteDb::class.java,
                    "Notes").build()
            }
            return noteDb!!
        }
    }
}

