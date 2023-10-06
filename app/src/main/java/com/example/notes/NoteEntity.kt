package com.example.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    @ColumnInfo()
    var name : String? = null,

    @ColumnInfo()
    var notes : String? = null,

    @ColumnInfo
    var date: String? = null
)
