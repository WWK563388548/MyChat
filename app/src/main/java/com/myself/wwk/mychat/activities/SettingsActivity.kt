package com.myself.wwk.mychat.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import com.myself.wwk.mychat.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var mDatabase: DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null
    var mStorageRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        var userId = mCurrentUser!!.uid
        mDatabase = FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)

        mDatabase!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var userName = dataSnapshot!!.child("user_name").value
                var image = dataSnapshot!!.child("image").value
                var status = dataSnapshot!!.child("status").value
                var thumbImage = dataSnapshot!!.child("thumb_image").value

                // 获取用户名和状态
                settings_username.text = userName.toString()
                settings_status_tag.text = status.toString()


            }

            override fun onCancelled(dataBaseErrorSnapshot: DatabaseError?) {

            }
        })
    }
}
