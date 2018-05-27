package com.myself.wwk.mychat.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.myself.wwk.mychat.fragments.ChatsFragment
import com.myself.wwk.mychat.fragments.UsersFragment

/**
 * Created by wwk on 2018/5/27.
 */
class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return UsersFragment()
            }

            1 -> {
                return ChatsFragment()
            }

        }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "好友"
            1 -> return "信息"
        }
        return null!!
    }


}