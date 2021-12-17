package com.example.airvet.model

import java.io.Serializable

data class UserTimezone(
    val offset:String?,
    val description:String?
) :Serializable