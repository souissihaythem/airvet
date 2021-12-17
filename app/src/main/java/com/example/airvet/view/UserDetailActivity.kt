package com.example.airvet.view



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.airvet.R
import com.example.airvet.databinding.ActivityUserDetailBinding
import com.example.airvet.view.adapter.UserDetail
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_user_detail.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import androidx.core.content.ContextCompat
import com.example.airvet.utils.EXTRA_USER_DETAIL
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import org.jetbrains.anko.textColor
import com.google.android.gms.maps.model.CameraPosition

class UserDetailActivity : AppCompatActivity() ,OnMapReadyCallback{
    lateinit var userDetailBinding: ActivityUserDetailBinding
    private var mMap: GoogleMap? = null
    var latLong : LatLng ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        userDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(userDetailBinding.root)
        supportActionBar?.hide()

        val userDetail= intent?.extras?.getSerializable(EXTRA_USER_DETAIL) as UserDetail
        val coordinates = userDetail.location?.coordinates
        latLong = coordinates?.latitude?.toDouble()
            ?.let { latitude->
                coordinates.longitude?.toDouble()?.let { longitude -> LatLng(latitude, longitude) } }

        Glide.with(this).load(userDetail.picture?.large)
            .into(userDetailBinding.ivUser)


        if (userDetail.gender==getString(R.string.female)){
            userDetailBinding.ivUserBack.setImageResource(R.drawable.bg_female)
            userDetailBinding.tvAge.background = ContextCompat.getDrawable(this,R.drawable.bg_circle_red)
            userDetailBinding.ivUser.background = ContextCompat.getDrawable(this,R.drawable.bg_circle_red)
            userDetailBinding.tvUserName.textColor = ContextCompat.getColor(this, R.color.red)
            userDetailBinding.tvLocationName.textColor = ContextCompat.getColor(this, R.color.red)
            userDetailBinding.tvLocation.textColor = ContextCompat.getColor(this, R.color.red)
            userDetailBinding.tvStreetName.textColor = ContextCompat.getColor(this, R.color.red)
            userDetailBinding.tvNationality.textColor = ContextCompat.getColor(this, R.color.red)
            userDetailBinding.tvNationalityName.textColor = ContextCompat.getColor(this, R.color.red)
            userDetailBinding.tvJoined.textColor = ContextCompat.getColor(this, R.color.red)
            userDetailBinding.tvJoinedTime.textColor = ContextCompat.getColor(this, R.color.red)
        }

        val userCountry = userDetail.location?.country
        val userState = userDetail.location?.state
        val userCity = userDetail.location?.city
        val userStreetName = userDetail.location?.street?.name
        val userStreetNumber = userDetail.location?.street?.number
        val userRegisteredTime = userDetail.registered
        val userAge = userDetail.dob
        val title = userDetail.name?.title
        val first = userDetail.name?.first
        val last = userDetail.name?.last
        userDetailBinding.tvUserName.text = "$title $first $last"
        userDetailBinding.tvEmail.text = userDetail.email
        userDetailBinding.tvPhone.text = userDetail.phone
        userDetailBinding.tvCell.text = userDetail.cell
        userDetailBinding.tvJoinedTime.text = "$userRegisteredTime "+getString(R.string.years_ago)
        userDetailBinding.tvAge.text = "$userAge "+getString(R.string.years_old)
        userDetailBinding.tvLocationName.text = "$userCountry $userState $userCity"
        userDetailBinding.tvStreetName.text = "$userStreetName $userStreetNumber"
        userDetailBinding.tvNationalityName.text = userDetail.nat


        userDetailBinding.btnBack.setOnClickListener {
            finish()
          overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right)
        }
        (mapFragment as SupportMapFragment).getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.e("latlong",""+latLong)
            latLong?.let { latL->
                MarkerOptions()
                    .position(latL)
            }?.let { markerOptions ->
                mMap?.addMarker(
                    markerOptions
                )
            }

            mMap?.uiSettings?.isZoomGesturesEnabled = false
            mMap?.uiSettings?.isScrollGesturesEnabled = false
            val cameraPosition = latLong?.let { latL ->
                CameraPosition.Builder()
                    .target(latL).zoom(8f).build()
            }
        cameraPosition?.let { camPos ->
            CameraUpdateFactory
                .newCameraPosition(camPos)
        }?.let { camUpdate ->
            mMap?.animateCamera(
                camUpdate
            )
        }
            mMap?.setOnMarkerClickListener { true }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_out_right)
    }


}