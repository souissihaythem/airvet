package com.example.airvet.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.airvet.R
import com.example.airvet.databinding.ItemUsersBinding
import com.example.airvet.model.UserResult
import com.example.airvet.model.Users
import com.example.airvet.utils.EXTRA_USER_DETAIL
import com.example.airvet.view.UserDetailActivity

class UsersAdapter(private val users : UserResult): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUsersBinding.inflate(inflater,parent,false )
        return UserViewHolder(parent.context ,binding)
    }

    override fun getItemCount(): Int {
        return users.results.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.adapterView(users.results[position])
    }

    class  UserViewHolder (private  val context: Context, private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun adapterView (user: Users) {
            val title = user.name?.title
            val first = user.name?.first
            val last = user.name?.last
            binding.tvName.text = "$title $first $last"

            Glide.with(context).load(user.picture?.large)
                .into( binding.civProfile)

            val userDetail = UserDetail(user.name,user.picture,user.location,user.email,
                user.phone,user.cell,user.dob?.age,user.registered?.age,user.nat,user.gender
            )
            binding.root.setOnClickListener {
               val intent = Intent(context,UserDetailActivity::class.java)
                intent.putExtra(EXTRA_USER_DETAIL,userDetail)
                context.startActivity(intent)
                (context as AppCompatActivity).overridePendingTransition(R.anim.slide_left, R.anim.slide_right)

            }

        }
    }
}
