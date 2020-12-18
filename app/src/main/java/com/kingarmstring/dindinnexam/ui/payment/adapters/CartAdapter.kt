package com.kingarmstring.dindinnexam.ui.payment.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.payment.contracts.PaymentContract
import com.kingarmstring.dindinnexam.utils.Constants

class CartAdapter(
    val callback: PaymentContract,
    val requestManager: RequestManager,
    val list: List<MenuItem>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    //WHY NOT USING NEITHER ListAdapter or AsyncListDiffer - it needs real data to make
    //acceptable comparisons between data items when submitting lists.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                CartHeaderViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.cart_header_layout, parent, false)
                )
            }
            1 -> {
                CartViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.cart_list_item_layout, parent, false)
                )
            }
            else -> {
                CartFooterViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.cart_footer_layout, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CartViewHolder -> {
                holder.itemName.text = list[position-1].name
                holder.itemPrice.text = list[position-1].price.toString()
                holder.btnRemoveFromCartClickHandler(callback, position-1)
                requestManager.load(Constants.getImage(list[position-1].id)).into(holder.itemImage)
            }
            is CartHeaderViewHolder -> {
                //nothing should be done, static data holder
            }
            is CartFooterViewHolder -> {
                // calculation of the total in such case should be done in the backend.
                holder.totalPrice.text =
                    holder.totalPrice.context.getString(R.string.final_price_placeholder)
            }
        }
    }

    override fun getItemCount() = list.size + 2


    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = itemView.findViewById<ImageView>(R.id.cart_item_image)
        val itemName = itemView.findViewById<TextView>(R.id.item_name_text)
        val itemPrice = itemView.findViewById<TextView>(R.id.item_price_text)
        val button_cancel_item = itemView.findViewById<ImageButton>(R.id.cancel_item_button)

        fun btnRemoveFromCartClickHandler(callback: PaymentContract, index: Int) {
            button_cancel_item.setOnClickListener {
                callback.removeItemFromCart(index)
                Log.d("KingArmstring", "menuItem: $index")
            }
        }
    }

    override fun getItemViewType(position: Int) : Int {
        //in case I forgot, I need to replace the numbers with integer consts.
        return when (position) {
            0 -> 0
            list.size + 1 -> 2
            else -> 1
        }
    }

    class CartHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class CartFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val totalPrice: TextView = itemView.findViewById(R.id.text_total_price)
    }
}

