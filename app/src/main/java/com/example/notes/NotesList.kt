package com.example.notes

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentNotesListBinding
import java.time.LocalDateTime

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotesList : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentNotesListBinding
    lateinit var noteDb: NoteDb


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesListBinding.inflate(layoutInflater)
        noteDb = NoteDb.getInstance(requireActivity())

        val date = LocalDateTime.now().toString()
        binding.time.setText(date)

        binding.saveBtn.setOnClickListener {
            onGoClick()
        }

        binding.goBtn.setOnClickListener {
            var name = binding.name.text.toString()
            var notes = binding.notes.text.toString()
            var bundle = Bundle()
            bundle.putSerializable("name",name)
            bundle.putSerializable("notes",notes)

            findNavController().navigate(R.id.action_notesList_to_getNoteList,bundle)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onGoClick(){
        if (binding.name.text.isNullOrEmpty()){
            binding.name.error = "Enter name"
        }
        else if (binding.notes.text.isNullOrEmpty()){
            binding.notes.error = "Enter notes"
        }
        else{
            var noteEntity = NoteEntity(
                name = binding.name.text.toString(),
                notes = binding.notes.text.toString(),
                date = binding.time.text.toString()
            )
            Toast.makeText(requireActivity(), "Done", Toast.LENGTH_LONG).show()

            class SaveData : AsyncTask<Void, Void, Void>(){
                override fun doInBackground(vararg p0: Void?): Void? {
                    noteDb.noteDao().insert(noteEntity)
                    Log.d("TAG--->",noteDb.noteDao().insert(noteEntity).toString())
                    return null
                }
            }
            SaveData().execute()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}