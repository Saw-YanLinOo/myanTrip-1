package com.vmyan.myantrip.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav_menu.setItemSelected(R.id.nav_home,true)
        openFragment(HomeFragment())
        bottom_nav_menu.setOnItemSelectedListener {
            when(it){
                R.id.nav_plan -> openFragment(PlanFragment())
                R.id.nav_booking -> openFragment(BookingFragment())
                R.id.nav_home -> openFragment(HomeFragment())
                R.id.nav_communication -> openFragment(CommunicationFragment())
                R.id.nav_blog -> openFragment(BlogFragment())
            }
        }
        profile_image.setOnClickListener{
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        var uEmail = Hawk.get<String>("user_email")
        Toast.makeText(this,uEmail,Toast.LENGTH_SHORT).show()
    }

    public fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_containger,fragment)
        transaction.commit()
    }




}