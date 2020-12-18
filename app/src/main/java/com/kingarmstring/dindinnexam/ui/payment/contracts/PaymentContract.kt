package com.kingarmstring.dindinnexam.ui.payment.contracts

import com.kingarmstring.dindinnexam.models.MenuItem

interface PaymentContract {

    fun removeItemFromCart(index: Int)
}