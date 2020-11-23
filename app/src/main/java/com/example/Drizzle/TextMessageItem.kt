package com.example.Drizzle

import android.content.ClipData
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Item
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

class TextMessageItem(val message: TextMessage,
                      val context: Context)
    : Item() {
    override fun bind(viewHolder: GroupieViewHolder,
                      position: Int){
        //TO-DO unimplemented
    }

    override fun getLayout() = R.layout.item_text_message
}