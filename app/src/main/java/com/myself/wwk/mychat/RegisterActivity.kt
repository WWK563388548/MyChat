package com.myself.wwk.mychat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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

    }

}
