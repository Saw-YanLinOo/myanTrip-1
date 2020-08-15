package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.viewmodel.LoginVMFactory
import com.vmyan.myantrip.ui.viewmodel.LoginViewModel
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.coordinateButtonAndInputs
import com.vmyan.myantrip.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class Register : AppCompatActivity(), KeyboardVisibilityEventListener, DIAware {
    override val di: DI by closestDI()
    private val viewModelFactory: LoginVMFactory by instance()
    private val TAG = "RegisterActivity"

    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        KeyboardVisibilityEvent.setEventListener(this, this)

        var doubleBounce: Sprite = DoubleBounce();
        spin_kit_r.setIndeterminateDrawable(doubleBounce)

        coordinateButtonAndInputs(sign_up_btn, email_input_r,password_input_r, comfirm_password_input_r)
        setUpObserver()
    }

    @SuppressLint("ShowToast")
    private fun setUpObserver() {
        sign_up_btn.setOnClickListener(View.OnClickListener {
            val email = email_input_r.text.toString()
            val password = password_input_r.text.toString()
            val comfirm_password = comfirm_password_input_r.text.toString()
            if (!password.equals(comfirm_password)){
                applicationContext.showToast("passward and comfirm password must be equal")
            }else{
                viewModel.SignUp(email,comfirm_password).observe(this, Observer {
                    when (it) {
                        is Resource.Loading -> {
                            spin_kit_r.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            spin_kit_r.visibility = View.GONE
                            applicationContext.showToast("Login Successful")
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
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

    override fun onVisibilityChanged(isOpen: Boolean) {

    }
}