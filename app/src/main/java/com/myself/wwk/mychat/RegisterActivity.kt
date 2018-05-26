package com.myself.wwk.mychat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var mDataBase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
    }

    // Sign up button
    fun signUp(v: View) {

        var email = register_email.text.toString().trim()
        var userName  = register_username.text.toString().trim()
        var password = register_password.text.toString().trim()

        // 判断注册所需的资料是否为空
        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(userName) || !TextUtils.isEmpty(password)) {
            registerNewAccount(email, password, userName)
        } else {
            Toast.makeText(this, "请填写所有内容！", Toast.LENGTH_LONG)
                    .show()
        }
    }

    fun registerNewAccount(email: String, password: String, userName: String){

        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { 
                    task: Task<AuthResult> ->

                        if (task.isSuccessful){

                            var currentUser = mAuth!!.currentUser
                            var userId = currentUser!!.uid

                            /**
                             * 在database中创建Users表（table）
                             *
                             * e.g.: - Users(table)
                             *          - user_name: "xxxx"
                             *          - status: "...."
                             *          - image: image url
                             *          ....
                             */
                            mDataBase = FirebaseDatabase.getInstance().reference
                                    .child("Users").child(userId)

                            // 使用hashmap去构筑user对象
                            var userObject = HashMap<String, String>()
                            userObject.put("user_name", userName)
                            userObject.put("status", "你好")
                            userObject.put("image", "default")
                            userObject.put("thumb_image", "default")

                            // 给数据库赋值
                            mDataBase!!.setValue(userObject).addOnCompleteListener {
                                task: Task<Void> ->
                                if (task.isSuccessful){

                                    var dashBoardIntent = Intent(this, DashBoardActivity::class.java)
                                    dashBoardIntent.putExtra("name", userName)
                                    startActivity(dashBoardIntent)
                                    finish()

                                } else {
                                    Toast.makeText(this, "注册失败..", Toast.LENGTH_LONG)
                                            .show()
                                }
                            }

                        } else {
                            Toast.makeText(this, "无法注册!!!", Toast.LENGTH_LONG)
                                    .show()
                        }
                }
    }

}
