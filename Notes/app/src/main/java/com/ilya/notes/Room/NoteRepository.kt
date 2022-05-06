package com.ilya.notes.Room

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNoteToDB(note: Note)
    {
        noteDao.addNoteToDB(note)
    }

    suspend fun updateNote(note: Note)
    {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note)
    {
        noteDao.deleteNote(note)
    }

}