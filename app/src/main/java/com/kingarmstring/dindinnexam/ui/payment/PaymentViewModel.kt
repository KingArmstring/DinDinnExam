package com.kingarmstring.dindinnexam.ui.payment

import android.content.Context
import com.airbnb.mvrx.*
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.repository.PaymentRepository
import com.kingarmstring.dindinnexam.ui.payment.state.PaymentState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class PaymentViewModel @AssistedInject constructor(
    @Assisted state: PaymentState,
    private val paymentRepository: PaymentRepository,
) : BaseMvRxViewModel<PaymentState>(state, debugMode = true) {

    init {
        setState {
            copy(cartItems = Loading())
        }
    }

    fun getCartItems(context: Context) = paymentRepository.getCartItems(context)
        .execute {  state ->
            copy(cartItems = state)
        }

    fun removeItemFromCart(index: Int, context: Context) {
        paymentRepository.removeItemFromCart(index, context)
        getCartItems(context)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(state: PaymentState): PaymentViewModel
    }

    companion object : MvRxViewModelFactory<PaymentViewModel, PaymentState> {
        override fun create(viewModelContext: ViewModelContext,
                            state: PaymentState
        ): PaymentViewModel {
            return (viewModelContext.activity as PaymentActivity).viewModelFactory.create(state)
        }
    }
}