package com.example.notesapp.UI

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.model.NoteModel
import com.example.notesapp.R
import com.example.notesapp.data.NotesDatabase
import kotlinx.coroutines.launch


class NoteAdapter (val Notes : List<NoteModel>) : RecyclerView.Adapter <NoteAdapter.MyViewHolder> ()
{
     class MyViewHolder (val item : View) : RecyclerView.ViewHolder (item)
    {
      val title_txt  : TextView = item.findViewById<TextView>(R.id.tvTitle)
      val subtitle_txt  : TextView = item.findViewById<TextView>(R.id.tvContent)
      val removebtn  : ImageButton = item.findViewById(R.id.removebutton)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater
            .from(parent.context).
        inflate(R.layout.note_item_view , parent,false)
        return MyViewHolder(inflater)

    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
       val currentNote : NoteModel = Notes[position]
        holder.title_txt.text = currentNote.title
        holder.subtitle_txt.text = currentNote.subtitle
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, updateActivity::class.java)
            intent.putExtra("title",currentNote.title)
            intent.putExtra("content",currentNote.subtitle)
            intent.putExtra("id",currentNote.id)
            holder.itemView.context.startActivity(intent)
        }
        holder.removebtn.setOnClickListener {
            val context = holder.itemView.context
            val db = NotesDatabase.getInstance(context)

            // Launch a coroutine to delete
            kotlinx.coroutines.GlobalScope.launch(kotlinx.coroutines.Dispatchers.IO) {
                db.NoteDAO().delete(currentNote)
            }
        }

    }

    override fun getItemCount(): Int {
        return Notes.size
    }



}