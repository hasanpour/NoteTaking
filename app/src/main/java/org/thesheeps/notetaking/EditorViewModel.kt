package org.thesheeps.notetaking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.thesheeps.notetaking.data.NoteDatabase
import org.thesheeps.notetaking.data.NoteEntity

class EditorViewModel(app: Application) : AndroidViewModel(app) {

    private val database = NoteDatabase.getInstance(app)
    val currentNote = MutableLiveData<NoteEntity>()

    /**
     * Get note from database
     */
    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val note =
                    if (noteId != NEW_NOTE_ID) {
                        database?.noteDao()?.getNoteById(noteId)
                    } else {
                        NoteEntity() //create new note
                    }
                currentNote.postValue(note ?: NoteEntity())
            }
        }
    }

    /**
     * Save edited note to database or delete it if it is empty
     */
    fun updateNote() {
        currentNote.value?.let {
            it.text = it.text.trim()

            if (it.id == NEW_NOTE_ID && it.text.isEmpty()) {
                return
            }

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (it.text.isEmpty()) {
                        database?.noteDao()?.deleteNote(it) //delete empty note
                    } else {
                        database?.noteDao()?.insertNote(it) //save changes
                    }
                }
            }
        }
    }
}