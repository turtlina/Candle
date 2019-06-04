package test.hong8yung.com.candle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_tab_object.*
import kotlinx.android.synthetic.main.fragment_topmenu.*
import android.support.design.widget.TabLayout

class TopMenuFragment : Fragment(){
    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_topmenu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tab_layout.setupWithViewPager(pager)
        demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager)
        pager.adapter = demoCollectionPagerAdapter
    }
}


class DemoCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){
    private val fragmentTitleList = mutableListOf("Home","Chart","Board")
    override fun getCount(): Int = 3
    override fun getItem(i: Int): Fragment? {
        val fragment = DemoObjctFragment()
        fragment.arguments = Bundle().apply{
            putInt(ARG_OBJECT, i+1)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList[position]
    }
}
private const val ARG_OBJECT = "object"
class DemoObjctFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf{it.containsKey(ARG_OBJECT)}?.apply {
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}
