package com.myself.wwk.mychat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class DashBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        if (intent.extras != null) {
            var username = intent.extras.get("name")
            Toast.makeText(this, username.toString(), Toast.LENGTH_LONG)
                    .show()
        }
    }
}
