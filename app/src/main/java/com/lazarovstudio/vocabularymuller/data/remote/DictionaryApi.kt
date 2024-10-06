package com.lazarovstudio.vocabularymuller.data.remote

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.lazarovstudio.vocabularymuller.constants.DICTIONARY_NODE
import com.lazarovstudio.vocabularymuller.constants.MAIN_NODE
import com.lazarovstudio.vocabularymuller.data.remote.vo.DictionaryVO

class DictionaryApi {
    private val dbPathMain = Firebase.database.getReference(MAIN_NODE)

    //получаем данные с базы данных
    fun getDictionary(readDataInterface: ReadDataInterface) {
        dbPathMain.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dicArray = ArrayList<DictionaryVO>()
                for (item in snapshot.children) {
                    val dic = item.child(DICTIONARY_NODE).getValue(DictionaryVO::class.java)
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
    fun wordViewed(word: DictionaryVO) {
        dbPathMain.child(word.id.toString())
            .child(DICTIONARY_NODE).setValue(word)
    }

    interface ReadDataInterface {
        fun readData(list: List<DictionaryVO>)
    }
}