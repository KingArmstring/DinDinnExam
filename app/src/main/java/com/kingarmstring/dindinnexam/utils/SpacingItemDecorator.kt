package com.kingarmstring.dindinnexam.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kingarmstring.dindinnexam.R

class SpacingItemDecorator(val spacingHeight: Int = 50) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = spacingHeight
    }

}