package com.example.Drizzle

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.getField

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy {FirebaseFirestore.getInstance()}

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("UserList/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit){
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()){
                val newUser = User()
                newUser.setUserId(FirebaseAuth.getInstance().uid)
                newUser.setName("Jimmothy Jimbob")
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            }
            else
                onComplete()
        }
    }

    fun getCurrentUser(onComplete: (User) -> Unit){
        currentUserDocRef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(User::class.java))
                }
    }

    fun addGroupsListener(context: Context, onListen: (List<Group>) -> Unit): ListenerRegistration{
        return firestoreInstance.collection('UserList').document(FirebaseAuth.getInstance().uid)
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "Group listener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    val items = querySnapshot.getField<Array>('myGroup')
                    onListen(items)

                }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()
}