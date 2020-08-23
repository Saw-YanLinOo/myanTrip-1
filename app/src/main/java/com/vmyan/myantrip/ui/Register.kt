package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.realpacific.clickshrinkeffect.applyClickShrink
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.RegisterViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.coordinateButtonAndInputs
import com.vmyan.myantrip.utils.showToast
import kotlinx.android.synthetic.main.activity_register.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import org.koin.android.ext.android.inject

class Register : AppCompatActivity(), KeyboardVisibilityEventListener {
    private val TAG = "RegisterActivity"
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val loadingDialog = LoadingDialog(this)

    private val viewModel: RegisterViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        KeyboardVisibilityEvent.setEventListener(this, this)

        signupbtn.applyClickShrink()

        coordinateButtonAndInputs(signupbtn,signupnameinput, signupemailinput,signupphnuminput, signuppwinput,signupcompwinput)
        setUpObserver()

        gosigninbtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("ShowToast")
    private fun setUpObserver() {
        signupbtn.setOnClickListener(View.OnClickListener {
            val name = signupnameinput.text.toString()
            val email = signupemailinput.text.toString()
            val phonenum = signupphnuminput.text.toString()
            val password = signuppwinput.text.toString()
            val comfirm_password = signupcompwinput.text.toString()
            if (!password.equals(comfirm_password)){
                applicationContext.showToast("passward and comfirm password must be equal")
            }else{
                viewModel.SignUp(name,email,phonenum,comfirm_password).observe(this, Observer {
                    when (it) {
                        is Resource.Loading -> {
                            loadingDialog.startLoading()
                        }

                        is Resource.Success -> {
                            loadingDialog.stopLoading()
                            getUserAndSave()
                        }
                        is Resource.Failure -> {
                            loadingDialog.stopLoading()
                            applicationContext.showToast(it.message)
                            println(it.message)
                        }
                    }
                })
            }
        })
    }

    private fun getUserAndSave(){
        Log.e("Start Save","")
        viewModel.user(auth.currentUser!!.uid).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    loadingDialog.startLoading()
                }
                is Resource.Success -> {
                    loadingDialog.stopLoading()
                    val mUser = it.data
                    Log.e("Save User===>",it.data.toString())
                    Hawk.put("user_id",mUser.value!!.user_id)
                    Hawk.put("user_name",mUser.value!!.username)
                    Hawk.put("user_email",mUser.value!!.email)
                    Hawk.put("user_phone",mUser.value!!.phone_number)
                    Hawk.put("user_profile",mUser.value!!.profilephoto)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    finish()
                }
                is Resource.Failure -> {
                    loadingDialog.stopLoading()
                    Log.e("Save Error=====>",it.message)
                }
            }
        })
    }

    override fun onVisibilityChanged(isOpen: Boolean) {

    }
}