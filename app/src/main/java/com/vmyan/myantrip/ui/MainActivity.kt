package com.vmyan.myantrip.ui

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.infideap.drawerbehavior.Advance3DDrawerLayout
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.fragment.*
import com.vmyan.myantrip.utils.NoAccountDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_nav_header_layout.*
import kotlinx.android.synthetic.main.main_nav_layout.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private val noAccountDialog = NoAccountDialog(this)

    private val auth = FirebaseAuth.getInstance()
    private lateinit var drawer: Advance3DDrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_nav_layout)

        drawer = findViewById<View>(R.id.drawer_layout) as Advance3DDrawerLayout
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        drawer_layout.setViewScale(GravityCompat.START, 0.96f)
        drawer.setRadius(GravityCompat.START, 30f)
        drawer.setViewElevation(GravityCompat.START, 8f)
        drawer.setViewRotation(GravityCompat.START, 15f)

        nav_menu_btn.setOnClickListener {
            if (!drawer.isDrawerOpen(GravityCompat.START)){
                drawer.openDrawer(GravityCompat.START)
            }else{
                drawer.closeDrawer(GravityCompat.END)
            }
            nav_username.text = Hawk.get("user_name")
            Glide.with(this)
                .load(Hawk.get<String>("user_profile"))
                .into(nav_user_img)
        }


        bottom_nav_menu.setItemSelected(R.id.nav_booking,true)
        openFragment(BookingFragment())
        bottom_nav_menu.setOnItemSelectedListener {
            when(it){
                R.id.nav_plan -> openFragment(PlanFragment())
                R.id.nav_booking -> openFragment(BookingFragment())
                R.id.nav_home -> openFragment(HomeFragment())
                R.id.nav_communication -> openFragment(CommunicationFragment())
                R.id.nav_blog -> openFragment(BlogFragment())
            }
        }

        Glide.with(this).load(Hawk.get<String>("user_profile")).error(R.drawable.ic_account_circle).into(mainuserprofile)

        mainuserprofile.setOnClickListener{
            val userid = auth.currentUser?.uid.toString().trim()
            println("User id===>"+userid)

            if (userid == "null"){
                //Toast.makeText(applicationContext,"plz Login to see your profile",Toast.LENGTH_SHORT).show()
                noAccountDialog.noAccountDialog()
            }else{
                println("User id===>"+userid)
                val intent = Intent(this, Profile::class.java)
                intent.putExtra("user_id",auth.currentUser?.uid)
                startActivity(intent)
            }
        }
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_containger,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.drawer_exit -> {
                auth.signOut()
                Hawk.put("user_id","")
                Hawk.put("user_name","")
                Hawk.put("user_email","")
                Hawk.put("user_phone","")
                Hawk.put("user_profile","")
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                this.finishAffinity()
            }
        }
        return true
    }

}