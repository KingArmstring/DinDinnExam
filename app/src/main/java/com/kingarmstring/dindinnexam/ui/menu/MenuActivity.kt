package com.kingarmstring.dindinnexam.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.ui.payment.PaymentActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setPaymentClickHandler()
    }

    private fun setPaymentClickHandler() {
        menu_fab_id.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}