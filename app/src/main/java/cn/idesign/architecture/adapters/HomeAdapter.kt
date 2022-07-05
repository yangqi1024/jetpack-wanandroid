package cn.idesign.architecture.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.idesign.architecture.pages.main.home.RecommendFragment
import cn.idesign.architecture.pages.main.home.WenDaFragment

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecommendFragment()
            else -> WenDaFragment()
        }
    }
}