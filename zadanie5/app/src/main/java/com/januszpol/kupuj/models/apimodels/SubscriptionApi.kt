package com.januszpol.kupuj.models.apimodels

data class SubscriptionApi(
    val id: Int,
    val userId: Int,
    // TODO swap to date
    val startDate: Int,
    val duration: Int
)
