package com.vmyan.myantrip.ui

import android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
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
            var userid = auth.currentUser?.uid.toString().trim()
            println("User id===>"+userid)

            if (userid == "null"){
                //Toast.makeText(applicationContext,"plz Login to see your profile",Toast.LENGTH_SHORT).show()
                dialog()
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

    fun goLogin(){
        Hawk.put("skip",false)
        val intent = Intent(this,Login::class.java)
        startActivity(intent)
        this.finishAffinity()
    }

    fun dialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val mBuilder = MaterialAlertDialogBuilder(this)
        val dialog: AlertDialog = mBuilder
            .setTitle("Login Please to see your profile!!")
            .setMessage("Go to Login press Ok ")
            .setPositiveButton("OK") {
                    dialog, which -> goLogin()
            }.create()
        dialog.show()
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLUE)

    }
}