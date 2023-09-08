package com.kanyideveloper.realmdatabasedemo

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class UserData() : RealmModel {
    @PrimaryKey
    var id: String = ""

    @Required
    var name: String? = ""
    var age: String? = ""
    var city: String? = ""

    @Required
    var description: String? = ""
}
