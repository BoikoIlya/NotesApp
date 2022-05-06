package com.ilya.notes.Room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun addNoteToDB(note: Note)

   @Update
   suspend fun updateNote(note: Note)

   @Delete
   suspend fun deleteNote(note: Note)

   @Query("SELECT * FROM note_table ORDER BY id ASC")
   fun readAllData():LiveData<List<Note>>
}