package com.example.Drizzle.recyclerview.item

import android.content.Context
import com.example.Drizzle.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_group_info.*

class groupItem(val groupId: String,
                val groupName: String,
                private val context: Context)
    : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.GroupInfoTitle.text = groupName

    }

    override fun getLayout() = R.layout.activity_group_info
    }