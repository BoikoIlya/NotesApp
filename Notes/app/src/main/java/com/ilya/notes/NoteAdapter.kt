package com.ilya.notes


import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ilya.notes.Room.Note
import com.ilya.notes.databinding.ItemBinding

class NoteAdapter(val listener:Listener):RecyclerView.Adapter<NoteAdapter.ItemHolder>(){

      var nodeList =ArrayList<Note>()

   inner class ItemHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
      private  val binding  = ItemBinding.bind(itemView)

        fun bind(
            note: Note,
            listener: Listener,
        ) = with(binding) {
            tvName.text = note.info
            if(note.toggle==true) {checkBox.isChecked = true
                tvName.paintFlags = tvName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG}
            else if(note.toggle==false) { checkBox.isChecked = false
                tvName.paintFlags = 0x00
            }


            if(checkBox.isChecked) note.toggle = true
             //val position = nodeList[adapterPosition]
            itemView.setOnClickListener {
                listener.onClick(note, adapterPosition)
            }
             checkBox.setOnClickListener {
                 if(checkBox.isChecked) {tvName.paintFlags = tvName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                     nodeList[adapterPosition].toggle=true}
                 else {tvName.paintFlags = 0x00
                     nodeList[adapterPosition].toggle=false}
                 listener.updateMethod(adapterPosition)
             }



        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent,  false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(nodeList[position], listener)
    }

    override fun getItemCount() = nodeList.size

   fun addNode(note: List<Note>)
    {
        this.nodeList = note as ArrayList<Note>
        notifyDataSetChanged()
   }
    interface Listener
    {
        fun onClick(note: Note, position:Int)
        fun updateMethod(position:Int)
    }
}
