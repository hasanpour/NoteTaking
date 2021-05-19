package org.thesheeps.notetaking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.thesheeps.notetaking.data.NoteDatabase
import org.thesheeps.notetaking.data.NoteEntity
import org.thesheeps.notetaking.data.SampleDataProvider

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = NoteDatabase.getInstance(app)
    val notesList = database?.noteDao()?.getNotes()

    /**
     * Add sample data to database
     */
    fun addSampleData() {
        //Run on background thread
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.insertAll(SampleDataProvider.getNotes())
            }
        }
    }

    /**
     * Delete selected notes
     */
    fun deleteSelectedNotes(notes: List<NoteEntity>) {
        //Run on background thread
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.deleteSelectedNotes(notes)
            }
        }
    }

    /**
     * Delete All notes
     */
    fun deleteAllNotes() {
        //Run on background thread
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database?.noteDao()?.deleteAllNotes()
            }
        }
    }
}