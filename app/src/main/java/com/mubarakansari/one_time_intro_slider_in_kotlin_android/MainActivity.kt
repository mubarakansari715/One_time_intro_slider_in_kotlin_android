package com.mubarakansari.one_time_intro_slider_in_kotlin_android


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val introFragment1 = IntroFragment()
    private val introFragment2 = IntroFragment()
    private val introFragment3 = IntroFragment()
    lateinit var adapter : myPagerAdapter


    private lateinit var preference : SharedPreferences
    private val pref_show_intro = "Intro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preference = getSharedPreferences("IntroSlider" , Context.MODE_PRIVATE)

        if(!preference.getBoolean(pref_show_intro,true)){
            startActivity(Intent(this,DashBordActivity::class.java))
            finish()
        }
        introFragment1.setTitle("Welcome")
        introFragment2.setTitle("Mubarak")
        introFragment3.setTitle("Happy to see you!")


        adapter = myPagerAdapter(supportFragmentManager)
        adapter.list.add(introFragment1)
        adapter.list.add(introFragment2)
        adapter.list.add(introFragment3)

        view_pager.adapter = adapter
        btn_next.setOnClickListener {
            view_pager.currentItem++
        }

        btn_skip.setOnClickListener { goToDashboard() }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                if(position == adapter.list.size-1){
                    //lastPage
                    btn_next.text = "Done"
                    btn_next.setOnClickListener {
                        goToDashboard()
                    }
                }else{
                    //has next
                    btn_next.text = "Next"
                    btn_next.setOnClickListener {
                        view_pager.currentItem++
                    }
                }

                when(view_pager.currentItem){
                    0->{
                        indicator1.setTextColor(Color.BLACK)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.GRAY)
                    }
                    1->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.BLACK)
                        indicator3.setTextColor(Color.GRAY)
                    }
                    2->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.BLACK)
                    }
                }

            }

        })

    }

    fun goToDashboard(){
        startActivity(Intent(this,DashBordActivity::class.java))
        finish()
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro,false)
        editor.apply()
    }

    class myPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){

        val list : MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }

    }

}