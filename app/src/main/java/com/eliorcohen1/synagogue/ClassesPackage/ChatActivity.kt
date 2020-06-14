package com.eliorcohen1.synagogue.ClassesPackage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eliorcohen1.synagogue.CustomAdapterPackage.ChatAdapter
import com.eliorcohen1.synagogue.ModelsPackage.ChatMessage
import com.eliorcohen1.synagogue.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()
    val chatMessages = ArrayList<ChatMessage>()
    var chatRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initList()
        setViewListeners()
    }

    override fun onDestroy() {
        super.onDestroy()

        chatRegistration?.remove()
    }

    private fun setViewListeners() {
        button_send.setOnClickListener { sendChatMessage() }
    }

    private fun initList() {
        if (user == null)
            return

        list_chat.layoutManager = LinearLayoutManager(this)
        val adapter = ChatAdapter(chatMessages, user.uid)
        list_chat.adapter = adapter

        listenForChatMessages()
    }

    private fun listenForChatMessages() {
        chatRegistration = firestore.collection("messages")
                .addSnapshotListener { messageSnapshot, exception ->

                    if (messageSnapshot == null || messageSnapshot.isEmpty)
                        return@addSnapshotListener

                    chatMessages.clear()

                    for (messageDocument in messageSnapshot.documents) {
                        chatMessages.add(
                                ChatMessage(
                                        messageDocument["text"] as String,
                                        messageDocument["email"] as String,
                                        messageDocument["user"] as String,
                                        messageDocument.getTimestamp("timestamp")!!.toDate()
                                ))
                    }

                    chatMessages.sortBy { it.timestamp }
                    list_chat.adapter?.notifyDataSetChanged()
                }
    }

    private fun sendChatMessage() {
        val message = edittext_chat.text.toString()
        edittext_chat.setText("")

        if (message != "") {
            firestore.collection("messages")
                    .add(mapOf(
                            Pair("text", message),
                            Pair("email", user?.email),
                            Pair("user", user?.uid),
                            Pair("timestamp", Timestamp.now())
                    ))
        }
    }

}
