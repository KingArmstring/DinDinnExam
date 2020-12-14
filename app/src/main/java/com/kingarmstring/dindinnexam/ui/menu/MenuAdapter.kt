package com.kingarmstring.dindinnexam.ui.menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingarmstring.dindinnexam.R
import com.kingarmstring.dindinnexam.models.MenuItem
import com.kingarmstring.dindinnexam.ui.menu.contracts.PizzaContract

class MenuAdapter(val menuItems: List<MenuItem>,
                  val callback: PizzaContract) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MenuViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_list_item_layout, parent, false))

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.tvItemName.text = menuItems[position].name
        holder.tvItemDesc.text = menuItems[position].desc
        holder.tvNutritionFacts.text = menuItems[position].nutritionFacts
        holder.btnAddToCart.text = menuItems[position].price.toString()
        holder.btnAddToCart(callback, menuItems[position])
        holder.menuItemImage.setImageResource(getImage(position))
    }

    override fun getItemCount() = menuItems.size

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuItemImage = itemView.findViewById<ImageView>(R.id.menu_item_image)
        val tvItemName = itemView.findViewById<TextView>(R.id.item_title_text)
        val tvItemDesc = itemView.findViewById<TextView>(R.id.item_desc_text)
        val tvNutritionFacts = itemView.findViewById<TextView>(R.id.nutrition_facts_text)
        val btnAddToCart = itemView.findViewById<Button>(R.id.button_add_to_cart)

        @SuppressLint("ClickableViewAccessibility")
        fun btnAddToCart(callback: PizzaContract, pizza: MenuItem) {
            btnAddToCart.setOnTouchListener { view, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        btnAddToCart.text = btnAddToCart.context.getString(R.string.btn_added_text)
                    }
                    MotionEvent.ACTION_UP -> {
                        btnAddToCart.text = pizza.price.toString()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        btnAddToCart.text = pizza.price.toString()
                    }
                }
                return@setOnTouchListener false
            }
            btnAddToCart.setOnClickListener {
                callback.addToCartButtonClickCallback(pizza)
            }
        }
    }
    private fun getImage(pos: Int) : Int{
        return when(pos) {
            0 -> {R.drawable.pizza1}
            1 -> {R.drawable.pizza2}
            2 -> {R.drawable.pizza3}
            else -> {R.drawable.pizza4}
        }
    }
}