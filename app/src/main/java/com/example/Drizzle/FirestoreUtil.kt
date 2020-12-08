package com.example.Drizzle

import android.content.Context
import android.util.Log
import com.example.Drizzle.recyclerview.item.PersonItem
import com.example.Drizzle.recyclerview.item.TextMessageItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.xwray.groupie.kotlinandroidextensions.Item

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("UserList/${FirebaseAuth.getInstance().currentUser!!.uid
                ?: throw NullPointerException("UID is null.")}")

    private val groupListCollection: CollectionReference
        get() = firestoreInstance.collection("GroupList")

    private val chatChannelsCollectionRef = firestoreInstance.collection("GroupList")

    fun getCurrentUser(onComplete: (User) -> Unit){
        currentUserDocRef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(User::class.java)!!)
                }
    }

    fun addGroupsListener(context: Context, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("GroupList")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "Groups listener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }


                    //TODO GET GROUPLIST OF CURRENT USER
                                val items = mutableListOf<Item>()
                                querySnapshot!!.documents.forEach {
                                    if (/*it.id.toString() in grouplist*/true) {
                                        Log.d("groupID", it.id.toString())
                                        items.add(PersonItem(it.toObject(Group::class.java)!!, it.id, context))
                                    }
                                }
                                onListen(items)

                }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

    fun getOrCreateGroupChat(groupId: String,
                        onComplete: (channelId: String) -> Unit) {
        groupListCollection
                .document(groupId).get().addOnSuccessListener {
                    if (it.exists()) {
                        onComplete(it.id as String)
                        return@addOnSuccessListener
                    }
                    else
                        Log.d("GroupChatError", "error finding selected group")

                    /*val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

                    val newChannel = chatChannelsCollectionRef.document()
                    newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))

                    currentUserDocRef
                            .collection("engagedChatChannels")
                            .document(otherUserId)
                            .set(mapOf("channelId" to newChannel.id))

                    firestoreInstance.collection("UserList").document(otherUserId)
                            .collection("engagedChatChannels")
                            .document(currentUserId)
                            .set(mapOf("channelId" to newChannel.id))

                    onComplete(newChannel.id)*/
                }
    }

    fun getOrCreateChat(otherUserId: String,
                             onComplete: (channelId: String) -> Unit) {
        currentUserDocRef.collection("engagedChatChannels")
                .document(otherUserId).get().addOnSuccessListener {
                    if (it.exists()) {
                        onComplete(it["channelId"] as String)
                        return@addOnSuccessListener
                    }

                    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

                    val newChannel = chatChannelsCollectionRef.document()
                    newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))

                    currentUserDocRef
                            .collection("engagedChatChannels")
                            .document(otherUserId)
                            .set(mapOf("channelId" to newChannel.id))

                    firestoreInstance.collection("UserList").document(otherUserId)
                            .collection("engagedChatChannels")
                            .document(currentUserId)
                            .set(mapOf("channelId" to newChannel.id))

                    onComplete(newChannel.id)
                }
    }

    fun addChatMessagesListener(channelId: String, context: Context,
                                onListen: (List<Item>) -> Unit): ListenerRegistration {
        return chatChannelsCollectionRef.document(channelId).collection("messages")
                .orderBy("time")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    val items = mutableListOf<Item>()
                    querySnapshot?.documents?.forEach{
                        if (it["type"] == MessageType.TEXT)
                            items.add(TextMessageItem(it.toObject(TextMessage::class.java)!!, context))
                        //else
                            //TODO ADD IMAGE MESSAGE

                    }
                    onListen(items)
                }
    }

    fun sendMessage(message: Message, channelId: String){
        chatChannelsCollectionRef.document(channelId)
                .collection("messages")
                .add(message)
    }

}