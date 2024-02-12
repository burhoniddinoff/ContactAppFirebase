package com.example.contactappfirebase2.domain

import android.util.Log
import com.example.contactappfirebase2.data.AddContact
import com.example.contactappfirebase2.data.ContactUIData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor() {
    private val fireStore = Firebase.firestore
    val contactDataFlow = MutableSharedFlow<List<ContactUIData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val errorMessage = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    init {
        fireStore.collection("test").addSnapshotListener { value, error ->
            val data = ArrayList<ContactUIData>()
            value?.forEach {
                data.add(
                    ContactUIData(
                        it.id,
                        it.data.getOrDefault("fname", "default") as String,
                        it.data.getOrDefault("lname", "default") as String,
                        it.data.getOrDefault("phone", "!") as String
                    )
                )
            }

            contactDataFlow.tryEmit(data)

            // error message ->

        }
    }

    fun addContact(addContact: AddContact): Flow<Result<Unit>> = callbackFlow {
        fireStore
            .collection("test")
            .document(System.currentTimeMillis().toString())
            .set(AddContact(addContact.fName, addContact.lName, addContact.phone))
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }.addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    fun deleteContact(id: String): Flow<Result<Unit>> = callbackFlow {
        fireStore.collection("test")
            .document(id)
            .delete()
            .addOnSuccessListener {
                trySend(Result.success(Unit))
                Log.d("TTT", "Success")
            }.addOnFailureListener {
                trySend(Result.failure(it))
                Log.d("TTT", "Fail")
            }
//            .addOnCanceledListener {
//                Log.d("TTT", "on canceled")
//            }
        awaitClose()
        Log.d("TTT", "delete contact")
    }

    fun updateContact(data: ContactUIData): Flow<Result<Unit>> = callbackFlow {
        fireStore
            .collection("test")
            .document(data.docID)
            .set(AddContact(data.fName, data.lName, data.phone))
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }
}