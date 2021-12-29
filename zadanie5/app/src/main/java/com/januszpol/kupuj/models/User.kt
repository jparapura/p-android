package com.januszpol.kupuj.models

import io.realm.RealmObject

open class User (
    var id: Int? = null,
    var login: String = "",
    var email: String = "",
    var password: String = "",
    var realName: String = "",
    var age: Int = 0
) : RealmObject()