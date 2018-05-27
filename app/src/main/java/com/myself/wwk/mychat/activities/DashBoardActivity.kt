package com.myself.wwk.mychat.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.myself.wwk.mychat.R

class DashBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        // 修改DashBoard的title
        supportActionBar!!.title = "控制面板"

        if (intent.extras != null) {
            var username = intent.extras.get("name")
            Toast.makeText(this, username.toString(), Toast.LENGTH_LONG)
                    .show()
        }
    }
}
