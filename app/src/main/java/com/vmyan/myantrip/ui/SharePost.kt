package com.vmyan.myantrip.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.fragment.CameraFragment

class SharePost : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSIONS = 1
    private val REQUEST_CODE_READ_STORAGE = 2
    private val IMAGE_CAPTURE_CODE = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_post)

        openFragment(CameraFragment())
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.share_containger,fragment)
        transaction.commit()
    }

    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }

    /**
     * Runtime Permission
     */
    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            +
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            /* Ask for permission */
            // need to request permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ) {
                Snackbar.make(findViewById(android.R.id.content), "Please grant permissions to write data in sdcard", Snackbar.LENGTH_INDEFINITE)
                    .setAction("", View.OnClickListener {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
                    }).show()
            } else {
                /* Request for permission */
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
            }
        } else {
            showChooser()
        }
    }
}