package org.thesheeps.notetaking.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao?

    companion object {
        private var INSTANCE: NoteDatabase? = null

        /**
         * Return an [INSTANCE] of database if it was not null.
         * Create if it was null.
         *
         * Singleton pattern
         */
        fun getInstance(context: Context): NoteDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "notetaking.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}