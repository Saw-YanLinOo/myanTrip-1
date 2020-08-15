package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.ui.viewmodel.LoginVMFactory
import com.vmyan.myantrip.ui.viewmodel.LoginViewModel
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.coordinateButtonAndInputs
import com.vmyan.myantrip.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class Login : AppCompatActivity(), KeyboardVisibilityEventListener, DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: LoginVMFactory by instance()
    private val TAG = "LoginActivity"

    private lateinit var viewModel: LoginViewModel

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var user : User

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null){
            getUserAndSave()
            goNextActivity(MainActivity())
        }else{
            Toast.makeText(this, "please! create your account",Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        KeyboardVisibilityEvent.setEventListener(this, this)
        var doubleBounce:Sprite  = DoubleBounce();
        spin_kit.setIndeterminateDrawable(doubleBounce)

        coordinateButtonAndInputs(login_btn, email_input, password_input)
        setUpObserver()


    }

    private fun getUserAndSave(){
        viewModel.user.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    //spin_kit.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    val mUser = it.data
                    Hawk.put("user_id",mUser.value!!.user_id)
                    Hawk.put("user_name",mUser.value!!.username)
                    Hawk.put("user_email",mUser.value!!.email)
                    Hawk.put("user_phone",mUser.value!!.phone_number)
                    Hawk.put("user_profile",mUser.value!!.profilephoto)
                }
                is Resource.Failure -> {
                    Log.e("Save Error=====>",it.message)
                }
            }
        })
    }

    @SuppressLint("ShowToast")
    private fun setUpObserver() {
        login_btn.setOnClickListener(View.OnClickListener {
            val email = email_input.text.toString()
            val password = password_input.text.toString()
            viewModel.Login(email,password).observe(this, Observer {
                when (it) {
                    is Resource.Loading -> {
                        spin_kit.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        spin_kit.visibility = View.GONE
                        applicationContext.showToast("Login Successful")
                        getUserAndSave()
                        goNextActivity(MainActivity())
                    }
                    is Resource.Failure -> {
                        spin_kit.visibility = View.GONE
                        applicationContext.showToast(it.message)
                        println(it.message)
                    }
                }
            })
        })
        create_account_text.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, Register::class.java))
        })
        skip.setOnClickListener (View.OnClickListener {
            goNextActivity(MainActivity())
        })
    }

    private fun goNextActivity(activity : Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onVisibilityChanged(isOpen: Boolean) {
        if (isOpen) {
            skip.visibility = View.GONE
        } else {
            skip.visibility = View.VISIBLE
        }
    }
}

