package com.example.firebaserealtimedatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedatabase.R.*

class DataAdapter(
    private var data: List<Data>,
    private var itemClickListener: ItemCLickListener
) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    interface ItemCLickListener {
        fun onEditItemClick(data: Data)
        fun onDeleteItem(data: Data)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studid: TextView = itemView.findViewById(id.idTxt)
        val name: TextView = itemView.findViewById(id.nameTxt)
        val email: TextView = itemView.findViewById(id.emaiTxt)
        val subject: TextView = itemView.findViewById(id.sujectTxt)
        val birthdate: TextView = itemView.findViewById(id.birthDateTxt)

        val edit: ImageButton = itemView.findViewById(id.editBtn)
        val delete: ImageButton = itemView.findViewById(id.deleteBtn)
    }

    fun updateData(newData: List<Data>) {
        this.data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.studid.text = item.studid
        holder.name.text = item.name
        holder.email.text = item.email
        holder.subject.text = item.subject
        holder.birthdate.text = item.birthdate

        holder.edit.setOnClickListener {
            itemClickListener.onEditItemClick(item) // Corrected here
        }
        holder.delete.setOnClickListener {
            itemClickListener.onDeleteItem(item) // Corrected to call onDeleteItem
        }
    }
}
