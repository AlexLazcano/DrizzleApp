package com.example.Drizzle

import android.content.Context
import android.util.Log
import com.example.Drizzle.recyclerview.item.PersonItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.xwray.groupie.kotlinandroidextensions.Item

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance()}

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("UserList/${FirebaseAuth.getInstance().currentUser!!.uid
                ?: throw NullPointerException("UID is null.")}")

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
}