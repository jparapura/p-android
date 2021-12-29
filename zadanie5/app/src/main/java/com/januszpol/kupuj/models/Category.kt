package com.januszpol.kupuj.models

import io.realm.RealmObject

open class Category (
    var id: Int? = null,
    var name: String = ""
) : RealmObject()