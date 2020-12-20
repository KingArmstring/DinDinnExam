package com.kingarmstring.dindinnexam.ui.payment

import android.content.Context
import android.util.Log
import com.airbnb.mvrx.*
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.repository.PaymentRepository
import com.kingarmstring.dindinnexam.ui.payment.state.PaymentState
import com.kingarmstring.dindinnexam.utils.Event
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.FlowableSubscriber
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription

class PaymentViewModel @AssistedInject constructor(
    @Assisted state: PaymentState,
    private val paymentRepository: PaymentRepository,
) : BaseMvRxViewModel<PaymentState>(state, debugMode = true) {

    init {
        setState {
            copy(cartItems = Loading())
        }
    }

    fun getCartItems(context: Context) = paymentRepository.getCartItems(context) // we can use execute from mvrx
        .execute {  state ->
            copy(cartItems = state)
        }

    fun removeItemFromCart(index: Int, context: Context) {
        paymentRepository.removeItemFromCart(index, context)
            .subscribe(object : SingleObserver<List<MenuItem>> { // also we can use RxObserver like SingleObserver
                override fun onSubscribe(d: Disposable) {
                    //should collect subscriptions and dispose them inside onDestroy in the fragment.
                }

                override fun onSuccess(t: List<MenuItem>) {
                    setState {
                        copy(cartItems = Success(t), itemRemoved = Success(Event(index)))
                    }
                }

                override fun onError(e: Throwable) {
                    //errors should be listened to in the fragment, in normal MVI or MVVM, there will
                    //be a separate error DataState field(MVI) or LiveData(MVVM) but here should be separate
                    //Async object inside the state.
                }

            })
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