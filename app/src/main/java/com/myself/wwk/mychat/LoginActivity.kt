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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var mDataBase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
    }

    // SignIn Button
    fun signInExistingUser(v: View) {

        var email = login_email.text.toString().trim()
        var password = login_password.text.toString().trim()

        // 判断登陆所需的资料是否为空
        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
            loginUser(email, password)
        } else {
            Toast.makeText(this, "请填写所有内容！", Toast.LENGTH_LONG)
                    .show()
        }

    }

    // 登陆功能
    private fun loginUser(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { 
                    task: Task<AuthResult> ->
                    if (task.isSuccessful){

                        var userName = email.split("@")[0]
                        var dashBoardIntent = Intent(this, DashBoardActivity::class.java)
                        dashBoardIntent.putExtra("name", userName)
                        startActivity(dashBoardIntent)
                        finish()

                    } else {
                        Toast.makeText(this, "登陆失败..", Toast.LENGTH_LONG)
                                .show()
                    }
                }
    }

    // Register new user Button
    fun registerNewUser(v: View) {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        finish()
        startActivity(registerIntent)
    }

}
