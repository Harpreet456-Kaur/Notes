package com.example.notes

import android.R
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentGetNoteListBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GetNoteList : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentGetNoteListBinding
    lateinit var noteDb: NoteDb
    lateinit var noteAdapter: ArrayAdapter<NoteEntity>
    var noteList = ArrayList<NoteEntity>()
    var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetNoteListBinding.inflate(layoutInflater)

        noteDb = NoteDb.getInstance(requireActivity())
        noteAdapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, noteList)
        binding.list.adapter = noteAdapter
        getList()

        binding.list.setOnItemLongClickListener { parent, view, position, id ->
            class DeleteData : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg p0: Void?): Void? {
                    noteDb.noteDao().delete(noteList[position])
                    return null
                }
            }
            DeleteData().execute()
            return@setOnItemLongClickListener true
        }

        return binding.root
    }

    fun getList() {
        position = -1
        noteList.clear()

        class getData : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                noteList.addAll(noteDb.noteDao().getAll())
                return null
            }
        }
        getData().execute()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GetNoteList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}