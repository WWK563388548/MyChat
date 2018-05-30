package com.myself.wwk.mychat.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.myself.wwk.mychat.R
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {

    var mDatabase: DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        supportActionBar!!.title = "个人状态"

        // 如果已有个人状态, 显示在EditText
        if (intent.extras != null) {
            var oldStatus = intent.extras.get("status")
            status_update_edit.setText(oldStatus.toString())
        }
        // 若无个人状态, 显示默认语句
        if(intent.extras.equals(null)) {
            status_update_edit.setText("请输入你的个人状态")
        }

        // 点击更新状态按钮
        status_update_button.setOnClickListener {
            mCurrentUser = FirebaseAuth.getInstance().currentUser
            var userId = mCurrentUser!!.uid

            mDatabase = FirebaseDatabase.getInstance().reference
                    .child("Users")
                    .child(userId)

            var status = status_update_edit.text.toString().trim()

            // 数据库更新值的操作
            mDatabase!!.child("status")
                    .setValue(status).addOnCompleteListener{
                task: Task<Void> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "个人状态更新成功!", Toast.LENGTH_LONG)
                            .show()
                    startActivity(Intent(this, SettingsActivity::class.java))
                } else {
                    Toast.makeText(this, "个人状态更新失败..", Toast.LENGTH_LONG)
                            .show()
                }
            }
        }
    }
}
