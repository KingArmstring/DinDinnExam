package com.kingarmstring.dindinnexam.ui.payment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.menu.MenuActivity
import com.kingarmstring.dindinnexam.ui.menu.MenuViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import javax.inject.Inject

class PaymentActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: PaymentViewModel.Factory

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setFabClickHandler()
        setFullScreen()
    }

    private fun setFabClickHandler() {
        payment_fab_id.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    private fun setFullScreen() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, // FLAG_FULLSCREEN is deprecated only
                WindowManager.LayoutParams.FLAG_FULLSCREEN  // starting from Android R but still valid before R
            )
        }
    }

}