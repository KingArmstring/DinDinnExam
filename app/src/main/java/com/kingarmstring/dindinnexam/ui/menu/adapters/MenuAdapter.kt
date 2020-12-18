package com.kingarmstring.dindinnexam.ui.menu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.menu.contracts.PizzaContract
import com.kingarmstring.dindinnexam.utils.Constants

class MenuAdapter(
    val callback: PizzaContract,
    val requestManager: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var diffUtilsCallBack: DiffUtil.ItemCallback<MenuItem> =
        object : DiffUtil.ItemCallback<MenuItem>() {
            override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem == newItem
            }
        }

    var differ = AsyncListDiffer(this, diffUtilsCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return MenuHeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.menu_list_header_layout, parent, false)
            )
        }
        return MenuViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.menu_list_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MenuViewHolder) {
            holder.tvItemName.text = differ.currentList[position-1].name
            holder.tvItemDesc.text = differ.currentList[position-1].desc
            holder.tvNutritionFacts.text = differ.currentList[position-1].nutritionFacts
            holder.btnAddToCart.text = differ.currentList[position-1].price.toString()
            holder.btnAddToCart(callback, differ.currentList[position-1])
            requestManager.load(Constants.getImage(position-1)).into(holder.menuItemImage)
        }else if (holder is MenuHeaderViewHolder){
            holder.firstText.text = "Pizza"
            holder.secondText.text = "Sushi"
            holder.thirdText.text = "Drinks"
        }
    }

    override fun getItemViewType(position: Int) = if(position == 0) 0 else 1


    override fun getItemCount() = differ.currentList.size + 1
    //adding 1 for the header, why don't just leave the header outside the recyclerview? Because it's required that this header scrolls with the list.


    fun submitList(newList: List<MenuItem>) = differ.submitList(newList)

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuItemImage = itemView.findViewById<ImageView>(R.id.menu_item_image)
        val tvItemName = itemView.findViewById<TextView>(R.id.item_title_text)
        val tvItemDesc = itemView.findViewById<TextView>(R.id.item_desc_text)
        val tvNutritionFacts = itemView.findViewById<TextView>(R.id.nutrition_facts_text)
        val btnAddToCart = itemView.findViewById<Button>(R.id.button_add_to_cart)

        @SuppressLint("ClickableViewAccessibility")
        fun btnAddToCart(callback: PizzaContract, pizza: MenuItem) {
            btnAddToCart.setOnTouchListener { _, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        btnAddToCart.background =
                            ContextCompat.getDrawable(
                                btnAddToCart.context,
                                R.drawable.button_add_to_cart_background_pressed
                            )
                        btnAddToCart.text = btnAddToCart.context.getString(R.string.btn_added_text)
                    }
                    MotionEvent.ACTION_UP -> {
                        btnAddToCart.text = pizza.price.toString()
                        btnAddToCart.background =
                            ContextCompat.getDrawable(
                                btnAddToCart.context,
                                R.drawable.button_add_to_cart_background_unpressed
                            )
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        btnAddToCart.text = pizza.price.toString()
                        btnAddToCart.background =
                            ContextCompat.getDrawable(
                                btnAddToCart.context,
                                R.drawable.button_add_to_cart_background_unpressed
                            )
                    }
                }
                return@setOnTouchListener false
            }
            btnAddToCart.setOnClickListener {
                callback.addToCartButtonClickCallback(pizza)
            }
        }
    }

    class MenuHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstText = itemView.findViewById<TextView>(R.id.first_text)
        val secondText = itemView.findViewById<TextView>(R.id.second_text)
        val thirdText = itemView.findViewById<TextView>(R.id.third_text)
    }
}

