package com.myself.wwk.mychat.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.myself.wwk.mychat.R
import com.myself.wwk.mychat.adapters.SectionPagerAdapter
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoardActivity : AppCompatActivity() {

    var sectionAdapter: SectionPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        // 修改DashBoard的title
        supportActionBar!!.title = "控制面板"

        // 创建tablayout
        sectionAdapter = SectionPagerAdapter(supportFragmentManager)
        dash_ViewPager_Id.adapter = sectionAdapter
        main_tabs.setupWithViewPager(dash_ViewPager_Id)
        main_tabs.setTabTextColors(Color.WHITE, Color.GREEN)

        if (intent.extras != null) {
            var username = intent.extras.get("name")
            Toast.makeText(this, username.toString(), Toast.LENGTH_LONG)
                    .show()
        }
    }

    // 设置menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)


        return true
    }

    // 设置menu中的各个选项
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)

        if (item != null) {
            if (item.itemId == R.id.logout_id) {
                // 退出登陆
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            if (item.itemId == R.id.settings_id) {
                // 进入settingsActivity
                startActivity(Intent(this, SettingsActivity::class.java))

            }
        }

        return true
    }
}
