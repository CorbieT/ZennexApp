package ru.zennex.zennexapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.adapters.MainTabAdapter

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout!!.addTab(tabLayout!!.newTab().setText(resources.getString(R.string.list_fragment)).setIcon(activity!!.getDrawable(R.drawable.list_icon)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(resources.getString(R.string.scaling_fragment)).setIcon(activity!!.getDrawable(R.drawable.scaling_icon)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(resources.getString(R.string.map_fragment)).setIcon(activity!!.getDrawable(R.drawable.map_icon)))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(resources.getString(R.string.parsing_fragment)).setIcon(activity!!.getDrawable(R.drawable.parsing_icon)))

        val adapter = MainTabAdapter(childFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager!!.currentItem = p0!!.position
            }

        })
    }
}