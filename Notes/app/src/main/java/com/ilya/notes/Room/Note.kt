package com.ilya.notes.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date
import java.sql.Time

@Entity(tableName = "note_table")
data class Note  (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var info:String?=null,
    var toggle:Boolean?=null
    ) :Serializable





