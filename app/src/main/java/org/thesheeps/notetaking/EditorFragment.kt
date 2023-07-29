package org.thesheeps.notetaking

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.thesheeps.notetaking.databinding.EditorFragmentBinding

class EditorFragment : Fragment() {

    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs()
    private lateinit var binding: EditorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Display checkmark as up button
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }
        setHasOptionsMenu(true)

        //Change title of fragment
        requireActivity().title =
            if (args.noteId != NEW_NOTE_ID) {
                getString(R.string.edit_note)
            } else {
                getString(R.string.new_note)
            }

        //Handle back button pressed
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }
            }
        )

        //Get data from save instance state if any
        val savedNote = savedInstanceState?.getString(NOTE_TEXT_KEY)
        val cursorStartPosition = savedInstanceState?.getInt(CURSOR_POSITION_START_KEY) ?: 0
        val cursorEndPosition =
            savedInstanceState?.getInt(CURSOR_POSITION_END_KEY) ?: cursorStartPosition

        binding = EditorFragmentBinding.inflate(inflater, container, false)
        binding.editTextNote.setText("")

        //Setting text of edit text
        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)
        viewModel.currentNote.asLiveData().observe(viewLifecycleOwner, {
            binding.editTextNote.setText(savedNote ?: it.text)
            binding.editTextNote.setSelection(cursorStartPosition, cursorEndPosition)
        })
        viewModel.getNoteById(args.noteId)

//TODO: Use repeatOnLifecycle when androidx.lifecycle 2.4.0 released

//        viewLifecycleOwner.lifecycle.repeatOnLifecycle(STARTED) {
//            viewModel.currentNote.collect{
//                binding.editTextNote.setText(savedNote ?: it.text)
//                binding.editTextNote.setSelection(cursorStartPosition, cursorEndPosition)
//            }
//            viewModel.getNoteById(args.noteId)
//        }

        return binding.root
    }

    /**
     * Handle up button pressed
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Back to previous fragment
     */
    private fun saveAndReturn(): Boolean {

        //Hide keyboard
        val imm = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        //Save edited note to database
        viewModel.currentNote.value?.text = binding.editTextNote.text.toString()
        viewModel.updateNote()

        findNavController().navigateUp()
        return true
    }

    /**
     * Save text and cursor position on orientation change
     */
    override fun onSaveInstanceState(outState: Bundle) {
        with(binding.editTextNote) {
            outState.putString(NOTE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_START_KEY, selectionStart)
            outState.putInt(CURSOR_POSITION_END_KEY, selectionEnd)
        }
        super.onSaveInstanceState(outState)
    }
}