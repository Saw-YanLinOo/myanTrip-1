package com.vmyan.myantrip.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import kotlinx.android.synthetic.main.context_profile.*

class Profile : AppCompatActivity() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Yan Lin Oo"

        btn_logout.setOnClickListener(View.OnClickListener {
            auth.signOut()
            Hawk.put("skip",false)
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            this.finishAffinity()
        })
    }
}