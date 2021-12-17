package com.example.airvet.view.adapter

import com.example.airvet.model.UserLocation
import com.example.airvet.model.UserName
import com.example.airvet.model.UserPicture
import java.io.Serializable


class UserDetail (val name: UserName?, val picture :UserPicture?, val location : UserLocation?, val email:String?,
                  val phone:String?, val cell:String?, val dob:Int?, val registered:Int?, val nat : String?,
                  val gender:String?
)  : Serializable