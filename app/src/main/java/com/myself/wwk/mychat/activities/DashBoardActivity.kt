package com.myself.wwk.mychat.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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
}
