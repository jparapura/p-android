package com.januszpol.kupuj.models

import io.realm.RealmObject

open class Subscription (
    var id: Int? = null,
    var userId: Int = 0,
    // TODO swap to Date
    var startDate: Int = 0,
    var duration: Int = 0
) : RealmObject()