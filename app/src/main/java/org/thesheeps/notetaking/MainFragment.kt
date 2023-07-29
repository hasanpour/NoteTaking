package org.thesheeps.notetaking

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.thesheeps.notetaking.data.NoteEntity
import org.thesheeps.notetaking.databinding.MainFragmentBinding

class MainFragment : Fragment(), ListItemListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Do not display up button on main fragment
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //Show option menu
        setHasOptionsMenu(true)

        //Set title of fragment
        requireActivity().title = getString(R.string.app_name)

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Handle create new note button pressed
        binding.fabAddNote.setOnClickListener {
            onItemClick(NEW_NOTE_ID)
        }

        //Get selected notes from save instance state if any
        val selectedNotes =
            savedInstanceState?.getParcelableArrayList<NoteEntity>(SELECTED_NOTES_KEY)

        //Set recycler view style
        with(binding.recyclerViewNotes) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }

        //Show data on recycler view
        //TODO: Use repeatOnLifecycle when androidx.lifecycle 2.4.0 released
        viewModel.notesList?.asLiveData()?.observe(viewLifecycleOwner, {
            adapter = NotesListAdapter(it, this@MainFragment)
            binding.recyclerViewNotes.adapter = adapter
            binding.recyclerViewNotes.layoutManager = LinearLayoutManager(activity)
            adapter.selectedNotes.addAll(selectedNotes ?: emptyList())
        })

        return binding.root
    }

    /**
     * If any note has been selected show delete menu, otherwise show main menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId = if (this::adapter.isInitialized && //because it is a lateinit variable
            adapter.selectedNotes.isNotEmpty()
        ) {
            R.menu.menu_selected_items
        } else {
            R.menu.menu_main
        }
        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Handle click on option menu items
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sample_data -> addSampleData()
            R.id.action_delete -> deleteSelectedNotes()
            R.id.action_delete_all -> deleteAllNotes()
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Add sample data to recycler view
     */
    private fun addSampleData(): Boolean {
        viewModel.addSampleData()
        return true
    }

    /**
     * Delete selected notes from database
     */
    private fun deleteSelectedNotes(): Boolean {
        viewModel.deleteSelectedNotes(adapter.selectedNotes)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedNotes.clear()
            requireActivity().invalidateOptionsMenu() //show main menu
        }, 100) //wait 100ms because app delete items on background
        return true
    }

    /**
     * Delete all notes
     */
    private fun deleteAllNotes(): Boolean {
        viewModel.deleteAllNotes()
        return true
    }

    /**
     * Open edit fragment when click a note or fab
     */
    override fun onItemClick(noteId: Int) {
        val action = MainFragmentDirections.actionEditNote(noteId)
        findNavController().navigate(action)
    }

    /**
     * Refresh options menu to show delete menu
     */
    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }

    /**
     * Save selected notes on orientation change
     */
    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized) {
            outState.putParcelableArrayList(SELECTED_NOTES_KEY, adapter.selectedNotes)
        }
        super.onSaveInstanceState(outState)
    }
}