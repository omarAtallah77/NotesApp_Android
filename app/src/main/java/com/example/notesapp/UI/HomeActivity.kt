package com.example.notesapp.UI

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.data.NotesDatabase
import com.example.notesapp.model.NoteModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)


        val addButton: FloatingActionButton = findViewById(R.id.addbutton)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Go to AddNoteActivity
        addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)

        }

        val db = NotesDatabase.getInstance(applicationContext)

        // Collect Flow from Room (auto updates on DB change)
        lifecycleScope.launch(Dispatchers.IO) {
            db.NoteDAO().getall().collectLatest { notesList ->
                val adapter = NoteAdapter(notesList)

                // Switch to Main thread for UI update
                lifecycleScope.launch(Dispatchers.Main) {
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}
