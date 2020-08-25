package com.vmyan.myantrip.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.flipboard.bottomsheet.BottomSheetLayout
import com.flipboard.bottomsheet.commons.MenuSheetView
import com.google.android.material.snackbar.Snackbar
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.fragment.CameraFragment


class SharePost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_post)

        openFragment(CameraFragment())

    }

    fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.share_containger, fragment)
        transaction.commit()
    }
}