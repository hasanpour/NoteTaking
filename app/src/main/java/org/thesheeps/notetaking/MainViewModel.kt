package org.thesheeps.notetaking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.thesheeps.notetaking.data.NoteEntity
import org.thesheeps.notetaking.data.SampleDataProvider

class MainViewModel : ViewModel() {

    val notesList = MutableLiveData<List<NoteEntity>>()

    init {
        //Create some sample data
        notesList.value = SampleDataProvider.getNotes()
    }
}