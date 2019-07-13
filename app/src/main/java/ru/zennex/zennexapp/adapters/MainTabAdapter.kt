package ru.zennex.zennexapp.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import ru.zennex.zennexapp.fragments.ListFragment
import ru.zennex.zennexapp.fragments.MapFragment
import ru.zennex.zennexapp.fragments.ParsingFragment
import ru.zennex.zennexapp.fragments.ScalingFragment

class MainTabAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {

    private var fragments: MutableList<Fragment?> = mutableListOf()

    init {
        fragments.add(0, ListFragment())
        fragments.add(1, ScalingFragment())
        fragments.add(2, MapFragment())
        fragments.add(3, ParsingFragment())
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]!!
    }

    override fun getCount(): Int {
        return fragments.size
    }

//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        assert(0 <= position && position < fragments.size)
//        fm.beginTransaction().remove(fragments[position]!!).commit()
//        fragments[position] = null
//    }

//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val fragment = getItem(position)
//        fm.beginTransaction().add(container.id, fragment)
//            .commit()
//        return fragment
////        fragments[position] = super.instantiateItem(container, position) as Fragment
////        return fragments[position]!!
//    }

//    override fun getItem(position: Int): Fragment{
//        assert(0 <= position && position < fragments.size)
//        if (fragments[position] == null) {
//            fragments[position] = when(position) {
//                0 -> ListFragment()
//                1 -> ScalingFragment()
//                2 -> MapFragment()
//                3 -> ParsingFragment()
//                else -> ListFragment()
//            }
//        }
//        return fragments[position]!!
//    }

}