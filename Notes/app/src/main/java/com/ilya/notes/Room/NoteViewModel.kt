package com.ilya.notes.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application){
     val readAllData: LiveData<List<Note>>
private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun addNoteToDB(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNoteToDB(note)
        }
    }

    fun updateNote(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteNote(note)
        }
    }

}