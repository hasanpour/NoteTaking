package org.thesheeps.notetaking

/**
 * An interface for listen to click on notes or fab.
 * Also listen on selected note changed.
 */
interface ListItemListener {
    fun onItemClick(noteId: Int)
    fun onItemSelectionChanged()
}