package com.example.airvet.model

import java.io.Serializable

data class UserLocation (
       val street : UserStreet?,
       val city : String?,
       val state : String?,
       val country : String?,
       val postcode : Any?,
       val coordinates : UserCoordinates?,
       val timezone : UserTimezone?,
        ) : Serializable
