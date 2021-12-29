package com.januszpol.kupuj.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Product (
    var id: Int? = null,
    var name: String = "",
    var description: String = "",
    var rating: Int = 0
    // TODO
    //val thumbnail: Image
) : RealmObject()
