package com.example.fininfocomtask.Realm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanyideveloper.realmdatabasedemo.UserData
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import java.util.*

class MainViewModel : ViewModel() {
    private var realm: Realm = Realm.getDefaultInstance()

    val allNotes: LiveData<List<UserData>>
        get() = getAllNotes()

    fun addNote(name: String, userDescription: String,age:String,city:String) {
        realm.executeTransaction { r: Realm ->
            val userData = r.createObject(UserData::class.java, UUID.randomUUID().toString())
            userData.name = name
            userData.age = age
            userData.city = city
            userData.description = userDescription
            realm.insertOrUpdate(userData)
        }
    }

    private fun getAllNotes(): MutableLiveData<List<UserData>> {
        val list = MutableLiveData<List<UserData>>()
        val notes = realm.where(UserData::class.java).findAll()
        list.value = notes?.subList(0, notes.size)
        return list
    }

    fun updateNote(id: String, name: String,age:String,city:String,desc: String) {
        val target = realm.where(UserData::class.java)
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            target?.name = name
            target?.age = age
            target?.city = city
            target?.description = desc
            realm.insertOrUpdate(target)
        }
    }

    fun deleteNote(id: String) {
        val notes = realm.where(UserData::class.java)
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            notes!!.deleteFromRealm()
        }
    }

    fun deleteAllUser() {
        realm.executeTransaction { r: Realm ->
            r.delete(UserData::class.java)
        }
    }
}