package org.thesheeps.notetaking

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.thesheeps.notetaking.data.NoteDao
import org.thesheeps.notetaking.data.NoteDatabase
import org.thesheeps.notetaking.data.NoteEntity
import org.thesheeps.notetaking.data.SampleDataProvider

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: NoteDao
    private lateinit var database: NoteDatabase

    @Before
    fun createDb() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(appContext, NoteDatabase::class.java)
            .allowMainThreadQueries() //keeping everything in the foreground thread
            .build()

        dao = database.noteDao()!!
    }

    /**
     * Test database with sample data
     */
    @Test
    fun createNotes() {
        dao.insertAll(SampleDataProvider.getNotes())
        assertEquals(dao.getCount(), SampleDataProvider.getNotes().size)
    }

    /**
     * Test insert single item into database
     */
    @Test
    fun insertNote() {
        val testString = "This is a test"
        val note = NoteEntity()
        note.text = testString
        dao.insertNote(note)
        val savedNote = dao.getNoteById(1)
        assertEquals(savedNote?.id ?: 0, 1) //David's solution
        assertEquals(savedNote?.text, testString) //my solution
    }

    @After
    fun closeDb() {
        database.close()
    }
}