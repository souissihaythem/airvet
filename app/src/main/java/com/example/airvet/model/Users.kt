package com.example.airvet.model

data class Users (
    val gender : String?,
    val name : UserName?,
    val location : UserLocation?,
    val email : String?,
    val login : UserLogin?,
    val dob : UserDateOfBirth?,
    val registered : UserRegistered?,
    val phone : String?,
    val cell : String?,
    val id : UserId?,
    val picture : UserPicture?,
    val nat : String?
)