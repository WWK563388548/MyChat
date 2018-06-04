package com.myself.wwk.mychat.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.myself.wwk.mychat.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    var mCurrentUser: FirebaseUser? = null
    var mUsersDatabase: DatabaseReference? = null
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar!!.title = "个人简介"
        // 在ActionBar添加返回键（返回DashBoardActivity）
        // AndroidManifest也要修改
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.extras != null) {
            userId = intent.extras.get("userId").toString()
            mCurrentUser = FirebaseAuth.getInstance().currentUser
            mUsersDatabase = FirebaseDatabase.getInstance().reference
                    .child("Users")
                    .child(userId)

            setUpProfile()
        }
    }

    private fun setUpProfile(){
        mUsersDatabase!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var userName = dataSnapshot!!.child("user_name").value.toString()
                var status = dataSnapshot!!.child("status").value.toString()
                var image = dataSnapshot!!.child("image").value.toString()

                // 从数据库获取数据，添加到profile中
                profile_username.text = userName
                profile_status.text = status

                // 设置图片（使用picasso）
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.profile_image)
                        .into(profile_image)

            }

            override fun onCancelled(databaseError: DatabaseError?) {
                // 暂时空置
            }
            
        })
    }
}
