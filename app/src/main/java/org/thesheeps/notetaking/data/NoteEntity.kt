package org.thesheeps.notetaking.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.thesheeps.notetaking.NEW_NOTE_ID
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var date: Date,
    var text: String
) {
    constructor() : this(NEW_NOTE_ID, Date(), "")
    constructor(date: Date, text: String) : this(NEW_NOTE_ID, date, text)
}