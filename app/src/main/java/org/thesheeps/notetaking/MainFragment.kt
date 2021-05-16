package org.thesheeps.notetaking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.thesheeps.notetaking.databinding.MainFragmentBinding

class MainFragment : Fragment(), ListItemListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Do not display up button on main fragment
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Set recycler view style
        with(binding.recyclerViewNotes) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }

        //Show data on recycler view
        viewModel.notesList.observe(viewLifecycleOwner, Observer {
            adapter = NotesListAdapter(it, this@MainFragment)
            binding.recyclerViewNotes.adapter = adapter
            binding.recyclerViewNotes.layoutManager = LinearLayoutManager(activity)
        })

        return binding.root
    }

    /**
     * Open edit fragment when click a note
     */
    override fun onItemClick(noteId: Int) {
        val action = MainFragmentDirections.actionEditNote(noteId)
        findNavController().navigate(action)
    }
}