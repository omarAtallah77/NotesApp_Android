package com.example.notesapp.UI

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.R
import com.example.notesapp.data.NotesDatabase
import com.example.notesapp.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class updateActivity () : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)

        val titleTxt: EditText = findViewById(R.id.etTitle)
        val subtitleTxt: EditText = findViewById(R.id.etContent)
        val update: Button = findViewById(R.id.update)

        val oldtitle = intent.getStringExtra("title")
        val oldsubtitle = intent.getStringExtra("content")
        val Note_id = intent.getIntExtra("id", -1)

        titleTxt.setText(oldtitle)
        subtitleTxt.setText(oldsubtitle)

        update.setOnClickListener {
            val titleText = titleTxt.text.toString().trim()
            val subtitleText = subtitleTxt.text.toString().trim()

            if (titleText.isEmpty() || subtitleText.isEmpty()) {
                Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // ✅ get current date & time
            val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            val currentDate = formatter.format(Date())

            val note = NoteModel(
                id=Note_id ,
                title = titleText,
                subtitle = subtitleText,
                date = currentDate
            )

            // ✅ Insert into DB on background thread
            lifecycleScope.launch(Dispatchers.IO) {
                val db = NotesDatabase.getInstance(applicationContext)
                db.NoteDAO().update(note)

                launch(Dispatchers.Main) {
                    Toast.makeText(this@updateActivity, "Note updated!", Toast.LENGTH_SHORT).show()
                    finish() // close activity after saving
                }
            }
        }
    }
}
