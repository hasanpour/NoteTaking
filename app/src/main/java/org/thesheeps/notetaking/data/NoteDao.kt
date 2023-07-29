package org.thesheeps.notetaking.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

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
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT COUNT(*) FROM notes")
    fun getCount(): Int

    @Delete
    fun deleteSelectedNotes(notes: List<NoteEntity>): Int

    @Delete
    fun deleteNote(note: NoteEntity): Int

    @Query("DELETE FROM notes")
    fun deleteAllNotes(): Int
}