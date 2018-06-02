package com.myself.wwk.mychat.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.myself.wwk.mychat.R
import com.myself.wwk.mychat.activities.ChatActivity
import com.myself.wwk.mychat.activities.ProfileActivity
import com.myself.wwk.mychat.models.Users
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by wwk on 2018/6/1.
 */
class UsersAdapter(databaseQuery: DatabaseReference, var context: Context)
    : FirebaseRecyclerAdapter<Users, UsersAdapter.ViewHolder>(
        Users::class.java,
        R.layout.users_row,
        UsersAdapter.ViewHolder::class.java,
        databaseQuery) {
    override fun populateViewHolder(viewHolder: UsersAdapter.ViewHolder?, user: Users?, position: Int) {

        // 每个用户都有一个独特的firebase id
        var userId = getRef(position).key
        viewHolder!!.bindView(user!!, context)

        viewHolder.itemView.setOnClickListener {
            // 创建AlertDialog
            var options = arrayOf("用户简介", "发送信息")
            var builder = AlertDialog.Builder(context)
            builder.setTitle("选择操作")
            builder.setItems(options, DialogInterface.OnClickListener { dialogInterface, i ->
                var userName = viewHolder.userNameTxt
                var userStatus = viewHolder.userStatusTxt
                var userProfilePic = viewHolder.userProfilePicLink

                if (i == 0) {
                    //打开用户简介的ProfileActivity

                    var profileIntent = Intent(context, ProfileActivity::class.java)
                    profileIntent.putExtra("userId", userId)
                    context.startActivity(profileIntent)

                } else {
                    //发送消息到ChatActivity
                    var chatIntent = Intent(context, ChatActivity::class.java)
                    chatIntent.putExtra("userId", userId)
                    chatIntent.putExtra("name", userName)
                    chatIntent.putExtra("status", userStatus)
                    chatIntent.putExtra("profile", userProfilePic)
                    context.startActivity(chatIntent)
                }
            })

            // 显示AlertDialog
            builder.show()

        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var userNameTxt: String? = null
        var userStatusTxt: String? = null
        var userProfilePicLink: String? = null

        fun bindView(user: Users, context: Context) {
            var userName = itemView.findViewById<TextView>(R.id.users_name)
            var userStatus = itemView.findViewById<TextView>(R.id.users_status)
            var userProfilePic = itemView.findViewById<CircleImageView>(R.id.users_profile)

            //set the strings so we can pass in the intent
            userNameTxt = user.user_name
            userStatusTxt = user.status
            userProfilePicLink = user.thumb_image

            userName.text = user.user_name
            userStatus.text = user.status

            Picasso.get()
                    .load(userProfilePicLink)
                    .placeholder(R.drawable.profile_image)
                    .into(userProfilePic)
        }
    }
}