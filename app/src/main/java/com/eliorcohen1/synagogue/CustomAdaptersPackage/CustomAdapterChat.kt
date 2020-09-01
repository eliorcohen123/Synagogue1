package com.eliorcohen1.synagogue.CustomAdaptersPackage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eliorcohen1.synagogue.ModelsPackage.ChatModel
import com.eliorcohen1.synagogue.R
import kotlinx.android.synthetic.main.list_item_chat.view.*

class CustomAdapterChat(private val chatModels: List<ChatModel>, private val uid: String) : RecyclerView.Adapter<CustomAdapterChat.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return chatModels.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatMessage = chatModels[position]

        if (chatMessage.user == uid) {
            holder.itemView.textview_chat_sent.text = chatMessage.text
            holder.itemView.textview_chat_email_sent.text = chatMessage.email
            holder.itemView.textview_chat_received.visibility = View.GONE
        } else {
            holder.itemView.textview_chat_received.text = chatMessage.text
            holder.itemView.textview_chat_email_received.text = chatMessage.email
            holder.itemView.textview_chat_sent.visibility = View.GONE
        }
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
            inflater.inflate(R.layout.list_item_chat, parent, false)
    )

}
