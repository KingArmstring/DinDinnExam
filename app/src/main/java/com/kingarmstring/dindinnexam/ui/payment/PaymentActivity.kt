package com.kingarmstring.dindinnexam.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.ui.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setFabClickHandler()
    }

    private fun setFabClickHandler() {
        payment_fab_id.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}