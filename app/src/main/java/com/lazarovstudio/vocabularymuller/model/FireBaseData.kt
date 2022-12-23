package com.lazarovstudio.vocabularymuller.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lazarovstudio.vocabularymuller.constants.DICTIONARY_NODE
import com.lazarovstudio.vocabularymuller.constants.MAIN_NODE

class FireBaseData {
    private val dbPathMain = Firebase.database.getReference(MAIN_NODE)

    //получаем данные с базы данных
    fun getDictionary(readDataInterface: ReadDataInterface) {
        dbPathMain.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dicArray = ArrayList<Dictionary>()
                for (item in snapshot.children) {
                    val dic = item.child(DICTIONARY_NODE).getValue(Dictionary::class.java)
                    dicArray.add(dic!!)
                }
                readDataInterface.readData(dicArray)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FIREBASEERROR", "Failed to read value.", error.toException())
            }
        })
    }

    //запись в базу количества просмотров
    fun wordViewed(word: Dictionary, counter: Int) {
        dbPathMain.child(word.id.toString())
            .child(DICTIONARY_NODE).setValue(
                Dictionary(
                    word.id,
                    word.description,
                    word.word,
                    counter.toString(),
                    word.save
                )
            )
    }

    interface ReadDataInterface {
        fun readData(list: List<Dictionary>)
    }
}