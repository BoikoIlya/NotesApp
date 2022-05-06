package com.ilya.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilya.notes.Room.Note
import com.ilya.notes.Room.NoteViewModel
import com.ilya.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NoteAdapter.Listener{
    lateinit var binding: ActivityMainBinding
  private val adapter = NoteAdapter(this)
    private var message : ActivityResultLauncher<Intent>?=null
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(this, Observer { note->
       adapter.addNode(note) })


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.rcView.setHasFixedSize(true)

        init()
        swipeToDelete()
    }

    fun init()
    {
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter

        binding.addBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, ResultAct::class.java).apply {
                putExtra("position", adapter.nodeList.size)
            })
        }
    }

    private fun swipeToDelete()
    {
        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Delete item")
                builder.setMessage("Are you sure want to delete?")
                builder.setPositiveButton("Confirm"){dialog, which->
                    mNoteViewModel.deleteNote(adapter.nodeList[position])
                }
                builder.setNegativeButton("Cancel"){dialog, which->
                    adapter.notifyItemChanged(position)
                }
                builder.show()
            }
        }).attachToRecyclerView(binding.rcView)
    }

    override fun onClick(note: Note, position: Int) {
        startActivity(Intent(this@MainActivity, EditAct::class.java).apply {
            putExtra("obj", note)
        putExtra("position", position)})
    }

    override fun updateMethod(position:Int) {
            mNoteViewModel.updateNote(adapter.nodeList[position])
    }

}
