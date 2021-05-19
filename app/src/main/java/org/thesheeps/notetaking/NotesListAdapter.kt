package org.thesheeps.notetaking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.thesheeps.notetaking.data.NoteEntity
import org.thesheeps.notetaking.databinding.ListItemBinding

class NotesListAdapter(private val notesList: List<NoteEntity>, private val listener: ListItemListener) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    //Use to store selected note
    val selectedNotes = arrayListOf<NoteEntity>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    /**
     * Set item layout for recycler view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Show notes and handle on click of them
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        with(holder.binding) {
            textViewNote.text = note.text
            root.setOnClickListener {
                listener.onItemClick(note.id)
            }

            /** Show checkmark when select a note and add it to [selectedNotes] for future reference */
            fabSelectNote.setOnClickListener {
                if (selectedNotes.contains(note)) {
                    selectedNotes.remove(note)
                    fabSelectNote.setImageResource(R.drawable.ic_note)
                } else {
                    selectedNotes.add(note)
                    fabSelectNote.setImageResource(R.drawable.ic_check)
                }
                listener.onItemSelectionChanged()
            }

            //To retain selection on scroll
            fabSelectNote.setImageResource(
                if (selectedNotes.contains(note)) {
                    R.drawable.ic_check
                } else {
                    R.drawable.ic_note
                }
            )
        }
    }

    /**
     * return size of [notesList]
     */
    override fun getItemCount(): Int {
        return notesList.size
    }
}