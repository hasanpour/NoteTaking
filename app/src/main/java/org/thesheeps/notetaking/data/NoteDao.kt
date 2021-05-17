package org.thesheeps.notetaking.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * All database operations
 */
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //use REPLACE to update note with same id
    fun insertNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(notes: List<NoteEntity>)

    @Query("SELECT * FROM notes ORDER BY date ASC")
    fun getNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT COUNT(*) FROM notes")
    fun getCount(): Int
}