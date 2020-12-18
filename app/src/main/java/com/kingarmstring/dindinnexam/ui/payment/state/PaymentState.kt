package com.kingarmstring.dindinnexam.ui.payment.state

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.kingarmstring.dindinnexam.models.MenuItem

data class PaymentState(val cartItems: Async<List<MenuItem>> = Uninitialized) : MvRxState