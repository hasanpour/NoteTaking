package org.thesheeps.notetaking

import org.thesheeps.notetaking.data.NoteEntity

/**
 * Listen to click on note item
 */
interface ListItemListener {
    fun onItemClick(noteId: Int)
    fun onItemSelectionChanged()
}