package com.example.Drizzle.recyclerview.item

import android.content.Context
import com.example.Drizzle.R
import com.example.Drizzle.TextMessage
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class TextMessageItem (val message: TextMessage,
                        val context: Context)
    : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //TODO: Placeholder bind
    }

    override fun getLayout() = R.layout.item_text_message
    }