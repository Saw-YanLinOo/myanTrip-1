package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.LoginViewModel
import com.vmyan.myantrip.ui.viewmodel.RegisterViewModel
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

    private val viewModel: RegisterViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        KeyboardVisibilityEvent.setEventListener(this, this)

        var doubleBounce: Sprite = DoubleBounce();
        spin_kit_r.setIndeterminateDrawable(doubleBounce)

        coordinateButtonAndInputs(sign_up_btn,name_input_r, email_input_r,password_input_r, comfirm_password_input_r)
        setUpObserver()
    }

    @SuppressLint("ShowToast")
    private fun setUpObserver() {
        sign_up_btn.setOnClickListener(View.OnClickListener {
            val name = name_input_r.text.toString()
            val email = email_input_r.text.toString()
            val password = password_input_r.text.toString()
            val comfirm_password = comfirm_password_input_r.text.toString()
            if (!password.equals(comfirm_password)){
                applicationContext.showToast("passward and comfirm password must be equal")
            }else{
                viewModel.SignUp(name,email,comfirm_password).observe(this, Observer {
                    when (it) {
                        is Resource.Loading -> {
                            spin_kit_r.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            getUserAndSave()
                        }
                        is Resource.Failure -> {
                            spin_kit_r.visibility = View.GONE
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
                }
                is Resource.Success -> {
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
                    spin_kit_r.visibility = View.GONE
                    finish()
                }
                is Resource.Failure -> {
                    Log.e("Save Error=====>",it.message)
                }
            }
        })
    }

    override fun onVisibilityChanged(isOpen: Boolean) {

    }
}