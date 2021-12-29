package com.januszpol.kupuj.models

import io.realm.RealmObject

open class Order (
    var id: Int? = null,
    var userId: Int = 0,
    var productId: Int = 0
) : RealmObject()